package com.sparta.able.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LettuceLockFacade {

    private final RedisLockRepository redisLockRepository;
    private final Map<Class<?>, LockableService<?>> serviceMap;

    public <T> void decrease(Class<T> entityType, Long id, int amount) throws InterruptedException {
        // Lock 획득
        while (!redisLockRepository.lock(entityType.getSimpleName(), id)) {
            Thread.sleep(100);
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

    @SuppressWarnings("unchecked")
    private <T> LockableService<T> getService(Class<T> entityType) {
        LockableService<?> service = serviceMap.get(entityType);
        if (service == null) {
            throw new IllegalArgumentException("No service found for entity type: " + entityType.getName());
        }
        return (LockableService<T>) service;
    }


/*    public void decrease(Long id, int purchasedAmount) throws InterruptedException {
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
    }*/
}