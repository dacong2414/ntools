/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.config.cache;

import net.sf.ehcache.Ehcache;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.nuls.io.model.common.IGlobalConstant;

/**
 *
 * @author hhu
 * @version $Id: CacheConfiguration.java, v 0.1 2017年5月10日 上午11:03:40 hhu Exp $
 */
@Configuration
//标注启动缓存.
public class CacheConfiguration {
    /**
     * 获取到当前使用的默认缓存对象
     */
    @Bean
    public Ehcache ehCache(EhCacheCacheManager ehCacheCacheManager) {
        EhCacheFactoryBean ehcache = new EhCacheFactoryBean();
        ehcache.setCacheManager(ehCacheCacheManager.getCacheManager());
        ehcache.setCacheName(IGlobalConstant.DEFAULT_CACHE_NAME);
        ehcache.afterPropertiesSet();
        return ehcache.getObject();
    }

    /*
       * 据shared与否的设置,
       * Spring分别通过CacheManager.create()
       * 或new CacheManager()方式来创建一个ehcache基地.
       *
       * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
       *
       */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("conf/ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    /*
     * ehcache 主要的管理器
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }
}
