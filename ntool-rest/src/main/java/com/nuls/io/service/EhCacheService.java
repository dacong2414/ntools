package com.nuls.io.service;



/**
 * 缓存专用service 该service下所有方法都走缓存
 *
 * @author abj
 * @version $Id: EhCacheService.java, v 0.1 2012-11-5 下午04:49:10 abj Exp $
 */
public interface EhCacheService {
    /**
     * 删除所有缓存
     */
    public void refrushCache();

    /**
     * 根据方法名删除缓存(缓存的key)
     *
     * @param functionName 方法名
     */
    public void refrushCache(String functionName);



}