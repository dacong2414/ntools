package com.nuls.io.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.nuls.io.service.EhCacheService;

/**
 * 判断拦截的URL是否与数据库中记录的权限URL相匹配，没有则直接通过，如有则进入用户权限验证阶段
 * 
 * @author chaoyuhang
 * @version $Id: MySecurityMetadataSource.java, v 0.1 2014年7月11日 下午5:29:00 chaoyuhang Exp $
 */
@Component("mySecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);

    @Autowired
    EhCacheService              ehCacheService;

    //构造方法注入
    public MySecurityMetadataSource() {
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 返回该次请求资源所需要的权限，与数据库中的url进行匹配，如果匹配成功则进行权限验证，因此这方法决定url是否需要权限验证
     * 即调用@see MyAccessDecisionManager#decide(org.springframework.security.core.Authentication, Object, Collection)
     * @param object
     * @return
     * @throws IllegalArgumentException
     * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //从缓存取所有资源
        Map<String, Collection<ConfigAttribute>> resourceMap = ehCacheService.getAllResources();
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        logger.debug(">>>requestUrl is {} <<<", requestUrl);
        Collection<ConfigAttribute> string = null;
        // 检测请求与当前资源匹配的正确性  
        Iterator<String> iterator = resourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String uri = iterator.next();
            boolean flag = ehCacheService.getMatchResult(uri, requestUrl);
            logger.debug("{}  {}", uri, flag);
            if (flag) {
                string = resourceMap.get(uri);
                break;
            }
        }
        return string;
    }
}
