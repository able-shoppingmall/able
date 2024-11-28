package com.sparta.able.redis;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;
    /**
     * Redis 락 획득
     *
     * @param entityType 엔티티 타입
     * @param id         엔티티 ID
     * @return 락 획득 성공 여부
     */
    public Boolean lock(final String entityType, final Long id) {
        return redisTemplate
                .opsForValue()
                //setnx 명령어 사용 - key(key) value("lock")
                .setIfAbsent(generateKey(entityType, id), "lock", Duration.ofMillis(3_000));
    }
    /**
     * Redis 락 해제
     *
     * @param entityType 엔티티 타입
     * @param id         엔티티 ID
     * @return 락 해제 성공 여부
     */
    public Boolean unlock(final String entityType, final Long id) {
        return redisTemplate.delete(generateKey(entityType, id));
    }


    public String generateKey(final String entityType, final Long id) {
        return entityType + ":" + id;
    }
}