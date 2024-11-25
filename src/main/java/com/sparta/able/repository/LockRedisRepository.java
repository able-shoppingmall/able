package com.sparta.able.repository;

import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.SetArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LockRedisRepository {

    private final RedisCommands<String, String> redisCommands;

    public boolean acquireLock(String key, String value, long expireTime) {
        // NX: Key가 없을 경우에만 세팅, PX: Expire Time 설정
        SetArgs setArgs = SetArgs.Builder.nx().px(expireTime); // NX 옵션과 PX(Expire Time) 옵션 설정
        return "OK".equals(redisCommands.set(key, value, setArgs)); // set 메서드에 SetArgs 사용
    }

    public boolean releaseLock(String key, String value) {
        String currentValue = redisCommands.get(key);
        if (value.equals(currentValue)) {
            redisCommands.del(key);
            return true;
        }
        return false;
    }
}
