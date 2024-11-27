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
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Long couponId;

    @BeforeEach
    void setup() {
        // 테스트용 쿠폰 데이터 생성
        CouponRequestDto requestDto = new CouponRequestDto("Test Coupon", 10); // 쿠폰 수량 10개
        CouponResponseDto responseDto = couponService.createCoupon(requestDto);

        // 첫 번째 쿠폰만 가져오기
        Coupon coupon = couponRepository.findFirstByName("Test Coupon")
                .orElseThrow(() -> new IllegalStateException("Test coupon not found."));

        this.couponId = coupon.getId();
    }


    @Test
    void 동시성_테스트()  {
        int amount = 1; // 한 번에 감소시킬 쿠폰 수량
        int threadCount = 50; // 동시에 요청하는 사용자 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CyclicBarrier barrier = new CyclicBarrier(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    barrier.await(); // 모든 스레드가 준비될 때까지 대기
                    couponService.decrease(couponId, amount);
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

    @Test
    void 쿠폰_발급_테스트() {
        Long couponId = 1L;
        int amount = 1; // 한 번에 감소시킬 쿠폰 수량
        // 쿠폰 재고 초기화
        couponService.initializeCouponStock(couponId, 10);

        // Redis에서 초기 재고 값 확인
        String initialStockString = (String) redisTemplate.opsForValue().get("coupon_stock:" + couponId);
        Integer initialStock = (initialStockString != null) ? Integer.valueOf(initialStockString) : 0;
        System.out.println("초기 재고: " + initialStock);
        assertThat(initialStock).isEqualTo(10);  // 초기 재고가 10인지 확인

        // 쿠폰 발급 테스트
        for (int i = 0; i < 15; i++) {
            try {
                couponService.decrease(couponId, amount);
                // 쿠폰 발급 성공 시 출력
                String remainingStockString = (String) redisTemplate.opsForValue().get("coupon_stock:" + couponId);
                Integer remainingStock = (remainingStockString != null) ? Integer.valueOf(remainingStockString) : 0;
                System.out.println(" 남은 재고: " + remainingStock);
            } catch (Exception e) {
                // 발급 실패 시 출력
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
