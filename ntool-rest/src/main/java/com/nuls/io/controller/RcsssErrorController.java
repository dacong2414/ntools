/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.ResultInfo;

/**
 * 处理BaseException及其子类以外的其他异常，如404,500等等，包括但不限于主动抛出的runtimeException,Exception
 * @author hhu
 * @version $Id: LoginController.java, v 0.1 2017年5月10日 下午4:10:47 hhu Exp $
 */
@RestController
@ApiIgnore
public class RcsssErrorController implements ErrorController {
    private static final Logger logger  = LoggerFactory.getLogger(RcsssErrorController.class);

    private static final String ERRPATH = "/error";

    @RequestMapping(ERRPATH)
    public ResultInfo<String> error() {
        ResultInfo<String> ret = new ResultInfo<>();
        ret.setCode(CodeEnum.ERROR.getCode());
        ret.setMsg(CodeEnum.ERROR.getMsg());
        logger.debug("服务器内部异常");
        return ret;
    }

    @Override
    public String getErrorPath() {
        return ERRPATH;
    }

}
