package com.nuls.io.utils;

import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

/**
 * 取代理对象的原始对象
 *
 * @author cyh
 * @version $Id: ProxyUtils.java, v 0.1 2016年11月17日 下午5:28:32 cyh Exp $
 */
public class ProxyUtils {
    /**
     * 通过代理对象获取原始对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public static Object getTargetObject(Object proxy) {
        try {
            if (!AopUtils.isAopProxy(proxy)) {
                return proxy;//不是代理对象  
            }
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                return getJdkDynamicProxyTargetObject(proxy);
            } else { //cglib  
                return getCglibProxyTargetObject(proxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy;
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource()
            .getTarget();

        return target;
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

}
