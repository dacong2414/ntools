package com.nuls.io.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nuls.io.common.NeedEhcache;
import com.nuls.io.common.frame.FrameListUtils;
import com.nuls.io.common.frame.SqlQueryInfo;
import com.nuls.io.dao.LogonInfoDao;
import com.nuls.io.dao.SysResourcesDao;
import com.nuls.io.dao.SysRolesDao;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.entity.SysResources;
import com.nuls.io.service.EhCacheService;
import com.nuls.io.utils.StringUtils;

/**
 * 缓存实现类
 * @author abj
 * @version $Id: TestServiceImpl.java, v 0.1 2012-11-5 下午03:31:14 abj Exp $
 */
@Service
public class EhCacheServiceImpl extends BaseService implements EhCacheService {
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
    @Autowired
    UserDetailsService          userDetailsService;
    @Autowired
    LogonInfoDao                loginInfoDao;
    @Autowired
    SysResourcesDao             sysResourcesDao;
    @Autowired
    SysRolesDao                 sysRolesDao;

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

    @Override
    public Map<String, String> getAllCacheInfos() {
        Map<String, String> cacheMap = new HashMap<>();
        Cache cache = cacheManager.getCache(IGlobalConstant.DEFAULT_CACHE_NAME);
        List<?> list = cache.getKeys();
        for (int i = 0; i < list.size(); i++) {
            String cacheKey = String.valueOf(list.get(i));
            net.sf.ehcache.Element elment = ehCache.get(cacheKey);
            if (elment != null) {
                cacheMap.put(cacheKey, String.valueOf(elment.getHitCount()));
            }
        }
        return cacheMap;
    }

    @Override
    public Map<String, Collection<ConfigAttribute>> getAllResources() {
        Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
        //获取url资源
        List<SysResources> listDB = sysResourcesDao.getUrlResources();
        for (SysResources sysResources : listDB) {
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            //以权限名封装为Spring的security Object
            ConfigAttribute config = new SecurityConfig("ROLE_" + sysResources.getResourceId()
                                                        + "_" + sysResources.getResourceName());
            configAttributes.add(config);
            map.put(sysResources.getResourcePath(), configAttributes);
        }
        return map;
    }

    @Override
    public boolean checkAuth(HttpServletRequest request, Long uId) {
        Map<String, Collection<ConfigAttribute>> resourceMap = getAllResources();
        String requestUrl = request.getRequestURI();
        if (requestUrl.startsWith("..")) {
            requestUrl = requestUrl.substring(2, requestUrl.length());
        }
        Collection<ConfigAttribute> string = null;
        // 检测请求与当前资源匹配的正确性  
        Iterator<String> iterator = resourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String uri = iterator.next();
            boolean flag = com.nuls.io.utils.StringUtils.match(uri, requestUrl);
            logger.debug("{}  {}", flag, uri);
            if (flag) {
                string = resourceMap.get(uri);
                //找到匹配的第一个url之后，跳出循环
                break;
            }
        }
        //没有匹配到则放行
        if (string == null) {
            return true;
        }
        logger.info(">>>menuUrl is " + requestUrl + "<<<");
        LogonInfo user = loginInfoDao.getLoginInfoByUID(uId);
        Collection<GrantedAuthority> grantedAuths = getAuthorities(user);
        Iterator<ConfigAttribute> iterator2 = string.iterator();
        while (iterator2.hasNext()) {
            ConfigAttribute configAttribute = iterator2.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            logger.info("needPermission is " + needPermission);
            //用户所拥有的权限authentication
            for (GrantedAuthority ga : grantedAuths) {
                if (needPermission.equals(ga.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities(LogonInfo user) {
        //SET去重
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        //这里要查数据库，由于在remember me时 要先调用此方法   然而当前登录用户还没有初始化权限信息而报错
        List<SysResources> listSysresourcesDB = sysResourcesDao.getUrlResourcesByUid(user.getuId());
        for (SysResources SysResources : listSysresourcesDB) {
            authSet.add(new SimpleGrantedAuthority("ROLE_" + SysResources.getResourceId() + "_"
                                                   + SysResources.getResourceName()));
        }
        //给所有登录用户自动增加ROLE_USER权限
        authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authSet;
    }

    /**
    *
    * @param frameName  框架列表名称
    * @return
    */
    @Override
    @NeedEhcache
    public SqlQueryInfo getQueryBySqlId(String frameName) {
        return FrameListUtils.getQueryBySqlId(frameName);
    }

    @Override
    public boolean getMatchResult(String source, String target) {
        return StringUtils.match(source, target);
    }
}
