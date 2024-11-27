package com.sparta.able.service;

import com.sparta.able.repository.LockRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LockService {

    private final LockRedisRepository lockRedisRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean acquireLock(String lockKey, long expireTime) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCK", expireTime, TimeUnit.MILLISECONDS);
        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
