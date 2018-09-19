package com.nuls.io.config.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.nuls.io.common.EhRedisCache;
import com.nuls.io.model.common.IGlobalConstant;

/**
 * 开启EnableCaching 注解方式缓存
 * redis和ehcache缓存方法
 *
 * @author ambition
 * @version $Id: EhRedisConfig.java, v 0.1 2017年9月5日 下午2:29:24 ambition Exp $
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class EhRedisConfig extends CachingConfigurerSupport {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private Ehcache                       ehCache;
    /**
     * 是否激活redis服务
     */
    @Value("${spring.redis.service-on}")
    private boolean                       serviceOn;

    /**
     * 构造spring 缓存管理器
     * @return 缓存管理器
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        //简单管理器
        SimpleCacheManager manager = new SimpleCacheManager();
        List<EhRedisCache> caches = new ArrayList<>();
        caches.add(ehRedisCache());
        //可以管理cache多实例
        manager.setCaches(caches);
        return manager;
    }

    /**
     * ehcache、redis组合类
     * @return redis cache
     */
    @Bean
    public EhRedisCache ehRedisCache() {
        EhRedisCache redisCache = new EhRedisCache();
        //设置ehcache
        redisCache.setEhCache(ehCache);
        //判断redis是否开启
        if (serviceOn) {
            //判断是否存活
            isAvailable();
            //设置redis模板
            redisCache.setRedisTemplate(redisTemplate);
            redisCache.setUseRedis(serviceOn);
        }
        //设置redisCache名称
        redisCache.setName(IGlobalConstant.DEFAULT_CACHE_NAME);
        return redisCache;
    }

    /**
     * 通过redisTemplate来查询信息。来判断redis服务是否可用
     */
    private void isAvailable() {
        redisTemplate.getClientList();
    }
}
