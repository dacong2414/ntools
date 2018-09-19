package com.nuls.io.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nuls.io.common.NeedResubmitCheck;
import com.nuls.io.model.common.ResultInfo;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.service.EhCacheService;
import com.nuls.io.service.SpringSecurityService;
import com.nuls.io.utils.StringUtils;

@RestController
@RequestMapping("cache")
@Api(tags = "CacheController", description = "缓存信息获取控制类")
public class CacheController {
    private static final Logger   logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private EhCacheService        ehCacheService;

    @Autowired
    private SpringSecurityService springSecurityService;

    @RequestMapping(value = "/cacheKeys", method = RequestMethod.POST)
    @ApiOperation(value = "获取缓存信息", notes = "获取缓存信息")
    @NeedResubmitCheck
    public ResultInfo<Map<String, String>> getCacheKeys() {
        return ResultInfo.createSuccessResult(ehCacheService.getAllCacheInfos());
    }

    @RequestMapping(value = "/refrushCache", method = RequestMethod.POST)
    @ApiOperation(value = "刷新缓存", notes = "刷新缓存")
    @NeedResubmitCheck
    public ResultInfo<String> refrushCache() {
        ehCacheService.refrushCache();
        return ResultInfo.createSuccessResult("刷新缓存成功");
    }

    @RequestMapping(value = "/ajaxTest", method = { RequestMethod.POST })
    @ApiOperation(value = "ajax登录检查", notes = "登录检查")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginName", value = "登录用户名,必填", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码,必填", required = true, paramType = "query", dataType = "String") })
    @NeedResubmitCheck
    public ResultInfo<LogonInfo> ajaxTest(HttpServletRequest request, HttpServletResponse response)
                                                                                                   throws Exception {

        return ResultInfo.createSuccessResult(springSecurityService
            .getByNameWithNoAuth(getParameter(request, "loginName")));
    }

    private String getParameter(HttpServletRequest request, String parameter) {
        String string = "";
        try {
            string = new String(request.getParameter(parameter).getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(StringUtils.outPutException(e));
        }
        return string;
    }
}
