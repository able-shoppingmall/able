package com.sparta.able.config.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCommands<String, String> redisCommands() {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379"); // Redis 서버 URL을 입력
        return redisClient.connect().sync(); // RedisCommands 빈 반환
    }
}

