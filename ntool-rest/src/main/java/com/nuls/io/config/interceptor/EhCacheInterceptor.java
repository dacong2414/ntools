package com.nuls.io.config.interceptor;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nuls.io.common.EhRedisCache;
import com.nuls.io.utils.ListUtils;

/**
 * AOP类定义了切点和通知，对Service/DAO的方法做了缓存
 *
 * @author Dale Chao
 * @version $Id: EhCacheInterceptor.java, v 0.1 2016年7月28日 下午6:05:05 Dale Chao Exp $
 */
@Aspect
@Order(3)
@Component
public class EhCacheInterceptor implements InitializingBean {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(EhCacheInterceptor.class);

    /**
     * @Resource 默认按Name匹配，找不到再按type 而@autowire是按type匹配的（缓存）
     */
    @Resource
    private EhRedisCache        ehRedisCache;

    /**
     * 切入点和通知合并写法
     * EhCacheService中所有get方法做缓存
     * @param pjp 目标对象
     * @return 缓存的对象
     * @throws Throwable
     */
    @Around(value = "@annotation(com.nuls.io.common.NeedEhcache)")
    public Object doAccess(ProceedingJoinPoint pjp) throws Throwable {
        return invoke(pjp);
    }

    /** 
     * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值， 
     * 否则，返回数据库查询结果，并将查询结果放入cache 
     *
     * @param pjp 目标对象
     * @return Object 缓存的对象
     * @throws Throwable
     */
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        String targetName = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object[] arguments = pjp.getArgs();

        Object result;

        logger.debug("从缓存中找到对应数据：{}", ehRedisCache.getName());

        String cacheKey = getCacheKey(targetName, methodName, arguments);
        ValueWrapper element = ehRedisCache.get(cacheKey);

        if (element == null) {
            logger.debug("挂起方法执行，创建新的缓存。");
            result = pjp.proceed();
            ehRedisCache.put(cacheKey, result);
            element = new SimpleValueWrapper(result);
        }
        //返回复制的对象，防止对象发生修改时污染缓存
        return ListUtils.deepClone(element.get());
    }

    /** 
     * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 
     * cache key包括 包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser 
     *
     * @param targetName 获取目标对象
     * @param methodName 方法名
     * @param arguments 参数
     * @return String 用上面三个参数拼接的字符串
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    /**
     * implement InitializingBean，检查cache是否为空 
     * @throws Exception
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ehRedisCache, "缓存为空，请创建新的缓存ehRedisCache。");
    }

}
