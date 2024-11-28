package com.sparta.able;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisConnection() {
        // given
        String testKey = "test-key";
        String testValue = "test-value";

        // when
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(testKey, testValue);

        // then
        String retrievedValue = valueOperations.get(testKey);
        assertThat(retrievedValue).isEqualTo(testValue);
    }
}