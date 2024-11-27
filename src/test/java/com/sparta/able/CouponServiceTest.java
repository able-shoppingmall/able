package com.sparta.able;


import com.sparta.able.service.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 동시성_쿠폰_발급_테스트() throws InterruptedException {
        int threadCount = 10000; // 1만 명이 동시에 요청
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    couponService.decrease(1L, 1); // 쿠폰 ID 1에 대해 발급 요청
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드 종료까지 대기
    }
}

