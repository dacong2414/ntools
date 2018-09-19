/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.ResultInfo;
import com.nuls.io.utils.NetworkUtil;

/**
 *  Ajax登录成功拦截器
 * @author cnambition
 * @version $Id: AjaxLoginAction.java, v 0.1 2016年5月3日 上午10:41:29 cnambition Exp $
 */
@Component("ajaxSuccessHandler")
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public AjaxAuthenticationSuccessHandler() {
    }

    /**
     * Ajax登录成功时，进入该方法获取失败提示数据
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException,
                                                                      ServletException {
        NetworkUtil.responseJSONMsg(response, ResultInfo.createResult(CodeEnum.SUCCESS));
    }
}