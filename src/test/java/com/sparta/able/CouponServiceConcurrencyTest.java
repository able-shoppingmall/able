package com.sparta.able;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;
import com.sparta.able.entity.Coupon;
import com.sparta.able.enums.CouponStatus;
import com.sparta.able.repository.CouponRepository;
import com.sparta.able.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponServiceConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    private Long couponId;

    @BeforeEach
    void setup() {
        // 테스트용 쿠폰 데이터 생성
        CouponRequestDto requestDto = new CouponRequestDto("Test Coupon", 10); // 쿠폰 수량 10개
        CouponResponseDto responseDto = couponService.createCoupon(requestDto);
        Coupon coupon = couponRepository.findByName("Test Coupon").orElseThrow();
        this.couponId = coupon.getId();
    }

    @Test
    void 동시성_테스트()  {
        int threadCount = 50; // 동시에 요청하는 사용자 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CyclicBarrier barrier = new CyclicBarrier(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    barrier.await(); // 모든 스레드가 준비될 때까지 대기
                    couponService.issueEventCoupon(couponId);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // 대기
        }

        // 쿠폰 데이터 검증
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        System.out.println("Remaining Coupons: " + coupon.getCount());
        assertThat(coupon.getCount()).isGreaterThanOrEqualTo(0); // 음수 여부 확인
    }
}
