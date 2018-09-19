package com.nuls.io.config.interceptor;

import java.lang.reflect.Method;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.nuls.io.common.NeedControllerLog;
import com.nuls.io.common.NeedServiceLog;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.entity.SysLog;
import com.nuls.io.model.exception.RcsssSecurityException;
import com.nuls.io.service.SysLogService;
import com.nuls.io.utils.StringUtils;

/**
 * 定义日志切面
 *
 * @author cyh
 * @version $Id: SystemLogAspect.java, v 0.1 2016年10月28日 下午3:52:59 cyh Exp $
 */
@Aspect
@Order(4)
@Component
public class SystemLogInterceptor {
    //注入Service用于把日志保存数据库  
    @Resource
    private SysLogService       logService;
    //本地异常日志记录对象  
    private static final Logger logger = LoggerFactory.getLogger(SystemLogInterceptor.class);

    //Service层切点  
    @Pointcut("@annotation(com.nuls.io.common.NeedServiceLog)")
    public void serviceAspect() {
    }

    //Controller层切点  
    @Pointcut("@annotation(com.nuls.io.common.NeedControllerLog)")
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
        //读取session中的用户  
        LogonInfo login = (LogonInfo) session.getAttribute(IGlobalConstant.CURRENT_USER);
        if (login == null || login.getUserInfo() == null) {
            throw new RcsssSecurityException(CodeEnum.ERROR_10000);
        }
        //请求的IP  
        String ip = getIpAddr(request);
        try {
            //*========数据库日志=========*//  
            SysLog log = new SysLog();
            log.setLogDesc(getControllerMethodDescription(joinPoint));
            log.setMethod((joinPoint.getTarget().getClass().getName() + "."
                           + joinPoint.getSignature().getName() + "()"));
            log.setLogType(IGlobalConstant.CONTROLLER_LOG);
            log.setRequestIp(ip);
            log.setExceptionCode(null);
            log.setExceptionDetail(null);
            log.setParams(null);
            log.setOperatorId(login.getuId());
            log.setOperateDate(Calendar.getInstance().getTime());

            //保存数据库  
            logService.addSysLog(log);
        } catch (Exception e) {
            //记录本地异常日志  
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e);
        }
    }

    /** 
     * 异常通知 用于拦截service层记录异常日志 
     * 
     * @param joinPoint 
     * @param e 
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        //读取session中的用户  
        LogonInfo login = (LogonInfo) session.getAttribute(IGlobalConstant.CURRENT_USER);
        if (login == null || login.getUserInfo() == null) {
            throw new RcsssSecurityException(CodeEnum.ERROR_10000);
        }
        //获取请求ip  
        String ip = getIpAddr(request);
        //获取用户请求方法的参数并序列化为JSON格式字符串  
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                try {
                    if (joinPoint.getArgs()[i] instanceof HttpServletResponse
                        || joinPoint.getArgs()[i] instanceof HttpServletRequest
                        || joinPoint.getArgs()[i] instanceof ModelMap) {
                        //以上类型不转换成json对象
                    } else {
                        params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
                    }

                } catch (Exception e2) {
                    logger.error("异常通知异常-转换参数异常（JSON.toJSONString(joinPoint.getArgs()[i])）:", e2);
                }
            }
        }
        try {
            /*==========数据库日志=========*/
            SysLog log = new SysLog();
            log.setLogDesc(getServiceMthodDescription(joinPoint));
            log.setExceptionCode(e.getClass().getName());
            log.setLogType(IGlobalConstant.SERVICE_LOG);
            log.setExceptionDetail(StringUtils.outPutException(e).substring(0, 2000));
            log.setMethod((joinPoint.getTarget().getClass().getName() + "."
                           + joinPoint.getSignature().getName() + "()"));
            log.setParams(params);
            log.setOperatorId(login.getuId());
            log.setOperateDate(Calendar.getInstance().getTime());
            log.setRequestIp(ip);
            //保存数据库  
            logService.addSysLog(log);
        } catch (Exception ex) {
            //记录本地异常日志  
            logger.error("异常通知异常:", ex);
        }
        /*==========记录本地异常日志==========*/
        logger.error("异常方法:{" + joinPoint.getTarget().getClass().getName()
                     + joinPoint.getSignature().getName() + "}");
        logger.error("异常代码:{" + e.getClass().getName() + "}");
        logger.error("异常信息:{" + e.getMessage() + "}");
        logger.error("参数:{" + params + "}");

    }

    /** 
     * 获取注解中对方法的描述信息 用于service层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
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
                    description = method.getAnnotation(NeedServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
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
                    description = method.getAnnotation(NeedControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取访问客户端IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(" x-forwarded-for ");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}