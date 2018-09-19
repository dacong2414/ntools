/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.ResultInfo;

/**
 * 
 *
 * @author 晁宇航
 * @version $Id: SwaggerRedirectController.java, v 0.1 2017年6月16日 下午4:35:55 晁宇航 Exp $
 */
@Controller
@ApiIgnore
public class BaseController {

    /**
     * swagger跳转
     *
     * @return
     */
    @RequestMapping(value = "/api")
    public String api() {
        return "redirect:swagger-ui.html";
    }

    /**
     * swagger跳转
     *
     * @return
     */
    @RequestMapping(value = "/db")
    public String db() {
        return "redirect:druid";
    }

    /**
     * 登录用户没有权限进此方法
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/authExp")
    @ResponseBody
    public ResultInfo<String> authExp(HttpServletRequest request, HttpServletResponse response) {
        return ResultInfo.createResult(CodeEnum.ERROR_20001);
    }

    /**
     * 未登录用户访问URL进此方法
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logon")
    @ResponseBody
    public ResultInfo<String> logon(HttpServletRequest request, HttpServletResponse response) {
        return ResultInfo.createResult(CodeEnum.ERROR_10000);
    }
}
