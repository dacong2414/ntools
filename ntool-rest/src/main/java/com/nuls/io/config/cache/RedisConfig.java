package com.nuls.io.config.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis的配置对象
 * 
 * @author qpan
 * 
 */
@Configuration
public class RedisConfig {
    @Autowired
    private Environment   env;
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPoolConfig(getRedisConfig());
        factory.setHostName(env.getProperty("spring.redis.host").trim());
        factory.setPort(Integer.parseInt(env.getProperty("spring.redis.port").trim()));
        String password = env.getProperty("spring.redis.password");
        if (StringUtils.isNotEmpty(password)) {
            factory.setPassword(password.trim());
        }
        factory.setDatabase(Integer.parseInt(env.getProperty("spring.redis.database").trim()));
        factory.setUsePool(true);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    public StringRedisSerializer getStringRedisSerializer() {
        return new StringRedisSerializer();
    }

    public GenericJackson2JsonRedisSerializer getGenericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setKeySerializer(getStringRedisSerializer());
        template.setValueSerializer(getGenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(getConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }
}