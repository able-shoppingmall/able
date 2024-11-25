package com.sparta.able.service;

import com.sparta.able.repository.LockRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LockService {

    private final LockRedisRepository lockRedisRepository;


    public boolean acquireLock(String key, String value, long expireTime) {
        return lockRedisRepository.acquireLock(key, value, expireTime);
    }

    public void releaseLock(String key, String value) {
        if (!lockRedisRepository.releaseLock(key, value)) {
            throw new IllegalStateException("Lock 해제 실패!");
        }
    }
}
