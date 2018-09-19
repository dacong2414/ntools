package com.nuls.io.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import com.nuls.io.common.frame.SqlQueryInfo;
import com.nuls.io.model.entity.LogonInfo;

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

    /**
     * 获取当前缓存中cache key值
     * @return
     */
    public Map<String, String> getAllCacheInfos();

    /**
     * 获取资源与权限关系
     * @return Map<String, List<ConfigAttribute>>
     */
    Map<String, Collection<ConfigAttribute>> getAllResources();

    /**
     * 获取用户的角色权限
     * @return
     */
    Set<GrantedAuthority> getAuthorities(LogonInfo user);

    /**
     * 判断当前用户是否具备访问路径的权限
     * @param uId 用户uId
     * @return boolean
     */
    boolean checkAuth(HttpServletRequest request, Long uId);

    /**
     * 根据框架列表名称获取查询语句，第一次查询将返回值保存进cache第二次查询直接从cache取值
     * @param frameName  框架列表名称
     * @return sqlQueryInfo
     */
    SqlQueryInfo getQueryBySqlId(String frameName);

    /**
     * 权限控制中的url匹配
     *
     * @param source
     * @param target
     * @return
     */
    public boolean getMatchResult(String source, String target);
}