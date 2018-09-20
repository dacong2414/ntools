package com.nuls.io.service.impl;


import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nuls.io.service.EhCacheService;
import com.nuls.io.utils.StringUtils;

/**
 * 缓存实现类
 * @author abj
 * @version $Id: TestServiceImpl.java, v 0.1 2012-11-5 下午03:31:14 abj Exp $
 */
@Service
public class EhCacheServiceImpl  implements EhCacheService {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(EhCacheServiceImpl.class);
    @Resource
    private Cache               ehCache;
    @Autowired
    CacheManager                cacheManager;
    @Autowired
    Environment                 env;

    /**
     * 删除所有缓存
     * 
     * @see com.abj.aboss.service.EhCacheService#refrushCache()
     */
    @Override
    public void refrushCache() {
        List<?> list = ehCache.getKeys();
        for (int i = 0; i < list.size(); i++) {
            String cacheKey = String.valueOf(list.get(i));
            ehCache.remove(cacheKey);
            logger.error("remove cache " + cacheKey);
        }
    }

    /**
     * 根据方法名刷新缓存
     * @param functionName 方法名
     * @see com.abj.afi.service.EhCacheService#refrushCache(java.lang.String)
     */
    @Override
    public void refrushCache(String functionName) {
        List<?> list = ehCache.getKeys();
        for (int i = 0; i < list.size(); i++) {
            String cacheKey = String.valueOf(list.get(i));
            if (cacheKey.lastIndexOf(functionName) >= 0) {
                ehCache.remove(cacheKey);
                logger.error("remove cache " + cacheKey);
            }
        }
    }




}
