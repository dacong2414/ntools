package com.nuls.io.config.interceptor;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.exception.ProcessException;

/**
 * 所有新增、修改、删除controller进行重复提交校验，需主动加上@NeedResubmitCheck注解
 *
 * @author 晁宇航
 * @version $Id: ResubmitCheckInterceptor.java, v 0.1 2017年6月19日 下午4:35:12 晁宇航 Exp $
 */
@Aspect
@Order(1)
@Component
public class ResubmitCheckInterceptor {
    //注入Service用于把日志保存数据库  
    //本地异常日志记录对象  

    //Controller层切点  
    @Pointcut("@annotation(com.nuls.io.common.NeedResubmitCheck)")
    public void controllerAspect() {
    }

    @Autowired
    HttpSession        session;
    @Autowired
    HttpServletRequest request;

    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        HashMap<String, Long> tHashMap = (HashMap<String, Long>) session
            .getAttribute(IGlobalConstant.CURRENT_URL_LIST);
        Long time = Calendar.getInstance().getTimeInMillis();

        String tokenValue = request.getRequestURL().toString();
        if (tHashMap == null) {
            tHashMap = new HashMap<>();
            session.setAttribute(IGlobalConstant.CURRENT_URL_LIST, tHashMap);
        }
        if (tHashMap.containsKey(tokenValue)
            && (time - tHashMap.get(tokenValue) < IGlobalConstant.CURRENT_URL_TIME_INTERVAL)) {
            throw new ProcessException(CodeEnum.ERROR_10002);

        }
        tHashMap.put(tokenValue, time);
    }
}