/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.exception;

import com.nuls.io.model.common.CodeEnum;

/**
 * Service,controller,dao等处理过程中主动抛出的异常
 * @author 晁宇航
 * @version $Id: ProcessException.java, v 0.1 2017年6月16日 下午7:29:36 晁宇航 Exp $
 */
public class ProcessException extends BaseException {

    /** */
    private static final long serialVersionUID = -2148009949107052123L;

    public ProcessException(CodeEnum code) {
        super(code);
    }
}
