package com.sparta.able.config.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.password}")
    private String password;


    // RedisConnectionFactory 설정 (Lettuce 사용)
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(6379); // Redis 기본 포트
        configuration.setPassword(password); // 비밀번호 설정

        return new LettuceConnectionFactory(configuration); // Redis 호스트와 포트 설정

        //return new LettuceConnectionFactory("localhost", 6379); // Redis 호스트와 포트 설정
    }

    // RedisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Key와 Value를 String으로 직렬화
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
    @Bean
    public RedisCommands<String, String> redisCommands() {
        // Redis 서버에 연결하기 위한 RedisClient 생성
        // RedisClient redisClient = RedisClient.create("redis://" + password + "@" + host + ":6379");
        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        return redisClient.connect().sync(); // RedisCommands 빈 반환
    }
}
