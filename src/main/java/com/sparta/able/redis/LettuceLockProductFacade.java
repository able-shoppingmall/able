package com.sparta.able.redis;

import com.sparta.able.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockProductFacade {

    private final RedisLockRepository redisLockRepository;
    private final ProductService productService;

    public void decrease(Long id, int purchasedAmount) throws InterruptedException {
        // Lock 획득
        while(!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }

        // Lock 획득에 성공했다면 재고 감소 로직 실행
        try {
            productService.decrease(id, purchasedAmount);
        } finally {
            // 로직이 모두 수행되었다면 Lock 해제
            redisLockRepository.unlock(id);
        }
    }
}