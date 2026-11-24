package ru.khalov.orderqueriesservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.khalov.orderqueriesservice.entity.Order;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate<String, Order> redisTemplate(
            RedisConnectionFactory factory,
            ObjectMapper mapper
    ){
        RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Order.class));

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
