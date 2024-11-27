package com.sparta.able.service;

import com.sparta.able.common.annotation.Locked;
import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;
import com.sparta.able.entity.Coupon;
import com.sparta.able.enums.CouponStatus;
import com.sparta.able.redis.LockableService;
import com.sparta.able.repository.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

//Redis 기반 선착순 쿠폰 발급
public class CouponService implements LockableService<Coupon> {

    private static final String COUPON_LOCK_KEY_PREFIX = "coupon_lock_";
    private static final long LOCK_EXPIRE_TIME = 5000L; // 5초
    private static final String COUPON_STOCK_KEY = "coupon_lock_";

    private final RedisTemplate<String, Object> redisTemplate;

    private final CouponRepository couponRepository;
    private final LockService lockService;

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
        String lockKey = COUPON_LOCK_KEY_PREFIX + couponId;
        String stockKey = COUPON_STOCK_KEY + couponId;

        // Redis 락 구현
        if (!lockService.acquireLock(lockKey, LOCK_EXPIRE_TIME)) {
            throw new IllegalStateException("쿠폰 발급 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.");
        }

        try {
            // 쿠폰 재고 확인 및 감소
            Long stock = redisTemplate.opsForValue().decrement(stockKey, amount);
            if (stock == null || stock < 0) {
                // 재고 부족 시 롤백
                redisTemplate.opsForValue().increment(stockKey, amount);
                throw new IllegalStateException("쿠폰이 모두 소진되었습니다.");
            }
        } finally {
            // 락 해제
            lockService.releaseLock(lockKey);
        }
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