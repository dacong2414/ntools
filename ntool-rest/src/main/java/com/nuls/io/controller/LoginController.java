/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hhu
 * @version $Id: LoginController.java, v 0.1 2017年5月10日 下午4:10:47 hhu Exp $
 */
@RestController
@Api(tags = "LoginController", description = "登录相关功能")
public class LoginController {
    @RequestMapping(value = "/logout", method = { RequestMethod.POST })
    @ApiOperation(value = "swagger登出", notes = "登出功能")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //实际/logout调用的是spring security自身的登出方法
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    @ApiOperation(value = "swagger登录验证", notes = "登录验证功能")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginName", value = "登录用户名,必填", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码,必填", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "_remember_me", value = "记住登录,非必填", required = false, paramType = "query", dataType = "boolean") })
    public void login(HttpServletRequest request, HttpServletResponse response) {
        //实际/logout调用的是spring security自身的登出方法
    }

}
