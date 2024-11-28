package com.sparta.able.service;

import com.sparta.able.common.annotation.Locked;
import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;
import com.sparta.able.entity.Coupon;
import com.sparta.able.enums.CouponStatus;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import com.sparta.able.redis.LockableService;
import com.sparta.able.redis.RedisLockRepository;
import com.sparta.able.repository.CouponRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Component
//Redis 기반 선착순 쿠폰 발급
public class CouponService implements LockableService<Coupon> {

    private static final String COUPON_LOCK_KEY_PREFIX = "coupon_lock_";
    private static final long LOCK_EXPIRE_TIME = 5000L; // 5초
    private static final String COUPON_STOCK_KEY = "coupon_stock_";

    private final RedisTemplate<String, Object> redisTemplate;

    private final CouponRepository couponRepository;
    private final RedisLockRepository redisLockRepository;



    // 사장님 쿠폰 생성
    public CouponResponseDto createCoupon(CouponRequestDto requestDto) {
        Coupon coupon = new Coupon();
        coupon.setName(requestDto.getName());
        coupon.setCount(requestDto.getCount());
        coupon.setStatus(CouponStatus.ACTIVE); // 기본 상태를 ACTIVE로 설정
        coupon.setStartAt(LocalDateTime.now());
        coupon.setEndAt(LocalDateTime.now().plusDays(30)); // 기본 만료 기간 30일

        couponRepository.save(coupon);

        // 생성된 쿠폰 정보를 반환
        return CouponResponseDto.builder()
                .name(coupon.getName())
                .count(coupon.getCount())
                .build();
    }
    /**
     * 쿠폰 발급 메서드
     */


    @Override
    @Locked
    public void decrease(Long couponId, int amount) {
        // 1. 데이터베이스에서 쿠폰 조회 및 검증
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_COUPON)
        );

        if (coupon.getCount() < amount) {
            throw new ApplicationException(ErrorCode.INSUFFICIENT_COUPON);
        }

        // 2. Redis에서 재고 감소
        String redisKey = "coupon_stock:" + couponId;
        Long redisStock = redisTemplate.opsForValue().decrement(redisKey, amount);

        if (redisStock == null || redisStock < 0) {
            // Redis 재고가 부족하면 복구 및 예외 처리
            redisTemplate.opsForValue().increment(redisKey, amount); // 복구
            throw new ApplicationException(ErrorCode.INSUFFICIENT_COUPON, "Redis 재고가 부족합니다.");
        }

        // 3. 데이터베이스에서 재고 감소
        coupon.decrease(amount);
        couponRepository.save(coupon);

        // 로그 출력
        System.out.println("Redis에서 읽은 쿠폰 재고: " + redisStock);
        System.out.println("데이터베이스에서 감소된 쿠폰 재고: " + coupon.getCount());
    }


    public <T> void decreaseWithLock(Class<T> entityType, Long id, int amount) throws InterruptedException {
        String lockKey = redisLockRepository.generateKey(entityType.getSimpleName(), id);

        long startTime = System.currentTimeMillis();

        while (!redisLockRepository.lock(entityType.getSimpleName(), id)) {
            if (System.currentTimeMillis() - startTime > LOCK_EXPIRE_TIME) {
                throw new ApplicationException(ErrorCode.LOCK_TIMEOUT, "Lock timeout occurred.");
            }
            Thread.sleep(100); // 100ms 간격으로 재시도
        }

        // Lock 획득에 성공했다면 로직 실행
        try {
            LockableService<T> service = getService(entityType);
            service.decrease(id, amount);
        } finally {
            // 로직이 모두 수행되었다면 Lock 해제
            redisLockRepository.unlock(entityType.getSimpleName(), id);
        }
    }

    private String getLockKey(Class<?> entityType, Long id) {
        return COUPON_LOCK_KEY_PREFIX + entityType.getSimpleName() + ":" + id;
    }

    @SuppressWarnings("unchecked")
    private <T> LockableService<T> getService(Class<T> entityType) {
        if (entityType.equals(Coupon.class)) {
            return (LockableService<T>) this;
        }
        throw new IllegalArgumentException("Unsupported entity type: " + entityType);
    }



    /**
     * 쿠폰 초기화 메서드 (테스트용)
     */
    public void initializeCouponStock(Long couponId, int stock) {
        String stockKey = COUPON_STOCK_KEY + couponId;
        redisTemplate.opsForValue().set("coupon_stock:" + couponId, String.valueOf(stock));
    }

    //쿠폰 발급 조회
    public List<CouponResponseDto> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(coupon -> new CouponResponseDto(
                        coupon.getName(),
                        coupon.getCount(),
                        calculateDiscount(coupon.getName()) // 예제 할인율 계산
                ))
                .collect(Collectors.toList());
    }

    private String calculateDiscount(String couponName) {
        // 할인율 계산 로직 (예: 쿠폰 이름에 따라 할인율 설정)
        if (couponName.contains(".*할인.*")) {
            return "10%";
        }
        return "5%";
    }
}