package com.nuls.io.config.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nuls.io.common.NeedAccessControl;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.UserInfo;
import com.nuls.io.model.exception.RcsssSecurityException;

/**
 * 
 *
 * @author cyh
 * @version $Id: SystemLogAspect.java, v 0.1 2016年10月28日 下午3:52:59 cyh Exp $
 */
@Aspect
@Order(2)
@Component
public class AccessControlInterceptor {
    //注入Service用于把日志保存数据库  
    //@Resource
    //private LogService          logService;
    //本地异常日志记录对象  

    //Service层切点  
    @Pointcut("@annotation(com.nuls.io.common.NeedAccessControl)")
    public void accessControl() {
    }

    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     * @throws Exception 
     */
    @Before("accessControl()")
    public void doBefore(JoinPoint joinPoint) throws Exception {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户  
        UserInfo user = (UserInfo) session.getAttribute(IGlobalConstant.CURRENT_USER);
        String servicePath = getAuth(joinPoint);
        if (!(user.getAuths().contains(servicePath))) {
            throw new RcsssSecurityException(CodeEnum.ERROR_20001);
        }
    }

    /**
     * 组装方法路径
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getAuth(JoinPoint joinPoint) throws Exception {
        String interFaceName = joinPoint.getSignature().toString();
        String path = joinPoint.getTarget().getClass().getName();
        String parameter = interFaceName.substring(interFaceName.indexOf("(") + 1,
            interFaceName.indexOf(")"));
        path = path + "." + joinPoint.getSignature().getName() + "(" + parameter + ")";
        return path;
    }

    /** 
     * 
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(NeedAccessControl.class).description();
                    break;
                }
            }
        }
        return description;
    }
}