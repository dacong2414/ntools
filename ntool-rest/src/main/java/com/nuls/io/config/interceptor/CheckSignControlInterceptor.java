package com.nuls.io.config.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.AccessKeyInfo;
import com.nuls.io.model.exception.ProcessException;
import com.nuls.io.service.SpringSecurityService;
import com.nuls.io.utils.HmacSha1;
import com.nuls.io.utils.StringUtils;

/**
 * 
 *
 * @author ambition
 * @version $Id: CheckSignControlInterceptor.java, v 0.1 2017年8月21日 下午2:50:38 ambition Exp $
 */
@Aspect
@Order(5)
@Component
public class CheckSignControlInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(CheckSignControlInterceptor.class);

    @Autowired
    HttpServletRequest          request;
    @Autowired
    SpringSecurityService       springSecurityService;

    //切点  
    @Pointcut("@annotation(com.nuls.io.common.CheckSignControl)")
    public void accessControl() {
    }

    /** 
     * 前置通知 用于拦截Controller层
     * 
     * @param joinPoint 切点 
     * @throws Exception 
     */
    @Before("accessControl()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //客户端数字签名密文
        String clientSign = "";
        Boolean booleanTimeOut = false;
        //请求时间（判断是否超时）
        String signDate = request.getHeader("SignDate");
        if (signDate != null) {
            booleanTimeOut = failToVerify(Long.valueOf(signDate));
        }
        //判断是否超时
        if (!booleanTimeOut) {
            throw new ProcessException(CodeEnum.ERROR_30005);
        }
        //请求类型
        String method = request.getMethod();
        //请求数字签名
        String authorization = request.getHeader("Authorization");
        //数据格式
        String contentType = request.getHeader("Content-Type");
        //GUID(消息唯一标识)
        String msgIdent = request.getHeader("MsgIdent");
        String[] attr = authorization.split(":");
        if (!StringUtils.isEmpty(authorization) && attr.length == 2) {
            clientSign = attr[1].trim();
            String[] str = attr[0].split("RCSSS");
            AccessKeyInfo accessKeyInfo = springSecurityService.getAccessKeyInfoById(str[1].trim());
            if (accessKeyInfo == null) {
                throw new ProcessException(CodeEnum.ERROR_30004);
            }
            //签名算法 默认utf-8
            String hmacShaString = method + "\n" + contentType + "\n" + signDate + "\n" + msgIdent;
            //系统根据签名算法算出的密文与传入密文进行对比校验
            byte[] hmcsha1After = HmacSha1.hamcsha1(hmacShaString.getBytes(), accessKeyInfo
                .getAccessKeySecret().getBytes());
            String sysBySign = HmacSha1.encodeData(hmcsha1After);
            if (!sysBySign.equals(clientSign)) {
                throw new ProcessException(CodeEnum.ERROR_30001);
            }
            LOG.error("客户端msgIdent=======" + msgIdent);
            Object[] arguments = joinPoint.getArgs();
            if (arguments.length > 0) {
                LOG.error("接收数据：======" + JSONObject.toJSONString(arguments[0]));
            } else {
                LOG.error("接收数据：====== 无body数据");
            }
        }
    }

    /** 
     * 发送日志打印
     * 
     * @param joinPoint 切点 
     * @throws Exception 
     */
    @AfterReturning(pointcut = "(@annotation(com.nuls.io.common.CheckSignControl))", returning = "rvt")
    public void doAfter(JoinPoint joinPoint, Object rvt) throws Exception {
        String string = JSONObject.toJSONString(rvt);
        LOG.error("发送数据：======" + string);
    }

    /**
     *失效时间验证 有效返回true
     * @param requestTime
     */
    private Boolean failToVerify(Long requestTime) {
        Calendar calendar = Calendar.getInstance();
        Long nowTime = calendar.getTimeInMillis();
        Long time = nowTime - requestTime;
        if (time < IGlobalConstant.SEND_MESSAGE_TIME_INTERVAL) {//有效返回true
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Calendar calendar = Calendar.getInstance();
        //签名算法 默认utf-8
        String hmacShaString = "POST" + "\n" + "application/json" + "\n"
                               + calendar.getTimeInMillis() + "\n" + "1";
        // String hmacShaString = "123456";
        //系统根据签名算法算出的密文与传入密文进行对比校验
        // String hamcsha1After = HmacSha1.hamcsha1(hmacShaString.getBytes(),
        //    "OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV".getBytes());
        // System.out.println("hamcsha1After" + hamcsha1After);
        String sysBySign = HmacSha1.encodeData(HmacSha1.hamcsha1(hmacShaString.getBytes(),
            "OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV".getBytes()));
        System.out.println("加密后：====##" + "RCSSS 44CF9590006BF252F707:" + sysBySign);
        System.out.println("现在时间：====" + calendar.getTimeInMillis());
    }
}