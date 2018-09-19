/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.ResultInfo;
import com.nuls.io.utils.NetworkUtil;

/**
 * Ajax登录失败拦截器
 *
 * @author zhangxi
 * @version $Id: AjaxAuthenticationFailureHandler.java, v 0.1 2016年5月25日 上午10:15:21 cnambition Exp $
 */
@Component("ajaxFailureHandler")
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    public AjaxAuthenticationFailureHandler() {
    }

    /**
     * Ajax登录失败时，进入该方法获取失败提示数据
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException,
                                                                          ServletException {
        NetworkUtil.responseJSONMsg(response, ResultInfo.createResult(CodeEnum.ERROR_10001));
    }
}