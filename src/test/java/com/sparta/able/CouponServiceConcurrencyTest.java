package com.sparta.able;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.entity.Coupon;
import com.sparta.able.repository.CouponRepository;
import com.sparta.able.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        // 테스트용 쿠폰 데이터 생성 (1000개 쿠폰 생성)
        CouponRequestDto requestDto = new CouponRequestDto("Test Coupon2", 1000); // 쿠폰 수량 1000개
        couponService.createCoupon(requestDto);

        // 첫 번째 쿠폰만 가져오기
        Coupon coupon = couponRepository.findFirstByName("Test Coupon2")
                .orElseThrow(() -> new IllegalStateException("Test coupon not found."));

        this.couponId = coupon.getId();

        // Redis 재고 초기화
        couponService.initializeCouponStock(couponId, 1000);
    }

    @Test
    void 대규모_동시성_쿠폰_발급_테스트() throws InterruptedException {
        int threadCount = 500; // 5000명의 동시 요청
        int amount = 1; // 한 번에 감소시킬 쿠폰 수량

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CyclicBarrier barrier = new CyclicBarrier(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    barrier.await(); // 모든 스레드가 준비될 때까지 대기
                    couponService.decreaseWithLock(Coupon.class, couponId, amount); // Redis Lock 활용한 감소
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100); // 모든 스레드가 종료될 때까지 대기
        }

        // 최종 쿠폰 재고 확인
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        System.out.println("최종 남은 쿠폰 수량: " + coupon.getCount());
        assertThat(coupon.getCount()).isGreaterThanOrEqualTo(0);

        // Redis 재고 확인
        String remainingStockString = (String) redisTemplate.opsForValue().get("coupon_stock:" + couponId);
        Integer remainingStock = (remainingStockString != null) ? Integer.valueOf(remainingStockString) : 0;
        System.out.println("Redis에 저장된 최종 재고: " + remainingStock);
        assertThat(remainingStock).isEqualTo(coupon.getCount());
    }
}
