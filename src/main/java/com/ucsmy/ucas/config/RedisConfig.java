package com.ucsmy.ucas.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 * Created by ucs_zhongtingyuan on 2017/4/11.
 */
@Configuration
public class RedisConfig {

    @Bean("keyStringRedisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> keyStringRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        return redisTemplate;
    }
/*    @Bean
    public JedisPoolConfig redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(6);
        jedisPoolConfig.setMaxWaitMillis(60000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMinIdle(3);
        return jedisPoolConfig;
    }*/

}
