/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.service.impl;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.exception.ProcessException;

/**
 *
 * @author 晁宇航
 * @version $Id: BaseService.java, v 0.1 2017年6月20日 上午11:54:18 晁宇航 Exp $
 */
@Service
public abstract class BaseService {

    @Autowired
    HttpSession        session;
    @Autowired
    HttpServletRequest request;

    /**
     * 取当前登录用户
     *
     * @return
     */
    LogonInfo getCurrentLoginInfo() {
        LogonInfo loginInfo = (LogonInfo) session.getAttribute(IGlobalConstant.CURRENT_USER);
        if (loginInfo == null) {
            throw new ProcessException(CodeEnum.ERROR_10000);
        }
        return loginInfo;

    }

    /**
     * 存登录用户到session
     *
     * @param loginInfo
     */
    void setCurrentLoginInfoToSession(LogonInfo loginInfo) {
        session.setAttribute(IGlobalConstant.CURRENT_USER, loginInfo);
    }

    /**
     * 向session中存值
     *
     * @param key
     * @param value
     * @return
     */
    <T> T addToSession(String key, T value) {
        session.setAttribute(key, value);
        return value;
    }

    /**
     * 向request中存值
     *
     * @param key
     * @param value
     * @return
     */

    <T> T addToRequest(String key, T value) {
        request.setAttribute(key, value);
        return value;
    }

    /**
     * 从session中取值
     *
     * @param key
     * @return
     */
    Object getFromSession(String key) {
        return session.getAttribute(key);
    }

    /**
     * 从request中取值
     *
     * @param key
     * @return
     */
    Object getFromRequest(String key) {
        return request.getAttribute(key);
    }

    /**
     * 向session中存值
     * @param key
     * @param value
     * @return
     */
    <T extends Serializable> T addToSession(String key, T value) {
        session.setAttribute(key, value);
        return value;
    }
}
