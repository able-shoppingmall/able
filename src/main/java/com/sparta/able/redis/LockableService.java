package com.sparta.able.redis;

public interface LockableService<T> {
    void decrease(Long id, int amount) throws InterruptedException;
}
