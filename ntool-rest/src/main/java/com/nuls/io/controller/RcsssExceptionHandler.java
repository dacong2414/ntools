/**
 * Ambition Inc.
 * Copyright (c) 2006-2016 All Rights Reserved.
 */
package com.nuls.io.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nuls.io.model.common.ResultInfo;
import com.nuls.io.model.exception.BaseException;

/**
 * 处理service，dao，controller等主动抛出的BaseException及其子类异常
 *
 * @author 晁宇航
 * @version $Id: ControllerInceptor.java, v 0.1 2017年6月20日 下午5:19:51 晁宇航 Exp $
 */
@RestControllerAdvice
public class RcsssExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RcsssExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultInfo<String> handleException(BaseException ex, HttpServletRequest request) {
        ResultInfo<String> ret = new ResultInfo<>();
        ret.setCode(ex.getCode().getCode());
        ret.setMsg(ex.getCode().getMsg());
        logger.debug("业务逻辑异常：{}", ex.getMessage());
        return ret;
    }
}
