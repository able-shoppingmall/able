package com.sparta.able.service;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;
import com.sparta.able.entity.Coupon;
import com.sparta.able.enums.CouponStatus;
import com.sparta.able.repository.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

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

    // 선착순 쿠폰 발급
    @Transactional
    public CouponResponseDto issueEventCoupon(Long couponId) {
        String lockKey = "coupon_" + couponId;  // Lock을 위한 고유 키
        String lockValue = "locked";            // Lock에 사용할 값

        // Lock을 획득
        if (!lockService.acquireLock(lockKey, lockValue, TimeUnit.SECONDS.toMillis(10))) {
            throw new IllegalStateException("다른 프로세스가 이미 쿠폰을 발급 중입니다.");
        }

        // 락 획득 시도
//        if (!lockService.acquireLock(lockKey, lockValue,5000)) { // 5초 동안 락 유지
//            throw new IllegalStateException("쿠폰 발급 요청이 너무 많습니다. 잠시 후 다시 시도해주세요.");
//        }

        try {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 쿠폰이 존재하지 않습니다."));

            if (coupon.getStatus() != CouponStatus.ACTIVE) {
                throw new IllegalStateException("이벤트 쿠폰이 발급 가능한 상태가 아닙니다.");
            }

            if (coupon.getCount() <= 0) {
                throw new IllegalStateException("모든 이벤트 쿠폰이 발급되었습니다.");
            }

            // 쿠폰 발급 처리
            coupon.setCount(coupon.getCount() - 1);
            if (coupon.getCount() == 0) {
                coupon.setStatus(CouponStatus.EXPIRED);
            }

            couponRepository.save(coupon);

            return CouponResponseDto.builder()
                    .name(coupon.getName())
                    .count(coupon.getCount())
                    .build();

        } finally {
            // 처리 완료 후 Lock 해제
            lockService.releaseLock(lockKey, lockValue);
        }
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
        if (couponName.contains("할인")) {
            return "10%";
        }
        return "5%";
    }
}
