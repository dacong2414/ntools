/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.security;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.service.SpringSecurityService;
import com.nuls.io.utils.DigestPass;
import com.nuls.io.utils.StringUtils;

/**
 * spring security Ajax登录验证，获取当前用户相关权限
 *  
 * @author zhangxi
 * @version $Id: AjaxAuthenticationProvider.java, v 0.1 2016年5月25日 上午10:16:10 cnambition Exp $
 */
public class AjaxAuthenticationProvider extends AbstractAuthenticationProcessingFilter {

    private static final Logger   logger                            = LoggerFactory
                                                                        .getLogger(AjaxAuthenticationProvider.class);

    //~ Static fields/initializers =====================================================================================

    public static final String    SPRING_SECURITY_FORM_USERNAME_KEY = "loginName";
    public static final String    SPRING_SECURITY_FORM_PASSWORD_KEY = "loginPwd";

    /**
    * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}
    */
    @Deprecated
    public static final String    SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String                usernameParameter                 = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String                passwordParameter                 = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean               postOnly                          = true;

    private SpringSecurityService springSecurityService;

    //~ Constructors ===================================================================================================
    /**
     * @param defaultFilterProcessesUrl
     */
    public AjaxAuthenticationProvider(SpringSecurityService springSecurityService) {
        super("/login");
        this.springSecurityService = springSecurityService;

    }

    //~ Methods ========================================================================================================

    /**
     * Ajax登录数据校验
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
                                                                             throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "
                                                     + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        logger.debug(">>>attemptAuthentication()-验证用户账号密码<<<");
        username = username.trim();
        //根据用户名到数据库取出用户数据
        LogonInfo user = springSecurityService.getByNameWithNoAuth(username);
        //验证登录用户信息
        if (user == null) {
            logger.error("用户不存在！");
            throw new AuthenticationServiceException("用户不存在！");
        }
        if (!user.getLoginPwd().equals(DigestPass.getDigestPassWord(password))) {
            logger.error("密码输入错误！");
            throw new AuthenticationServiceException("密码输入错误！");
        }
        if (user.getDisableFlag().equals(IGlobalConstant.DISABLED)) {
            logger.error("用户失效，无法登录！");
            throw new AuthenticationServiceException("用户失效，无法登录！");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            username, DigestPass.getDigestPassWord(password));

        //验证用户信息
        springSecurityService.validateUser(user);
        //登录之后具体操作，后期用service统一处理
        springSecurityService.initData(user);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
    * Enables subclasses to override the composition of the password, such as by including additional values
    * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
    * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
    * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
    *
    * @param request so that request attributes can be retrieved
    *
    * @return the password that will be presented in the <code>Authentication</code> request token to the
    *         <code>AuthenticationManager</code>
    */
    protected String obtainPassword(HttpServletRequest request) {
        String string = "";
        try {
            string = new String(request.getParameter(passwordParameter).getBytes("ISO-8859-1"),
                "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(StringUtils.outPutException(e));
        }
        return string;
    }

    /**
    * Enables subclasses to override the composition of the username, such as by including additional values
    * and a separator.
    *
    * @param request so that request attributes can be retrieved
    *
    * @return the username that will be presented in the <code>Authentication</code> request token to the
    *         <code>AuthenticationManager</code>
    */
    protected String obtainUsername(HttpServletRequest request) {
        String string = "";
        try {
            string = new String(request.getParameter(usernameParameter).getBytes("ISO-8859-1"),
                "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(StringUtils.outPutException(e));
        }
        return string;
    }

    /**
    * Provided so that subclasses may configure what is put into the authentication request's details
    * property.
    *
    * @param request that an authentication request is being created for
    * @param authRequest the authentication request object that should have its details set
    */
    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
    * Sets the parameter name which will be used to obtain the username from the login request.
    *
    * @param usernameParameter the parameter name. Defaults to "j_username".
    */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
    * Sets the parameter name which will be used to obtain the password from the login request..
    *
    * @param passwordParameter the parameter name. Defaults to "j_password".
    */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
    * Defines whether only HTTP POST requests will be allowed by this filter.
    * If set to true, and an authentication request is received which is not a POST request, an exception will
    * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
    * will be called as if handling a failed authentication.
    * <p>
    * Defaults to <tt>true</tt> but may be overridden by subclasses.
    */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }
}
