/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.exception;

import com.nuls.io.model.common.CodeEnum;


public class BaseException extends RuntimeException {

    /** */
    private static final long serialVersionUID = 7980605393981373039L;

    private CodeEnum          code;

    public BaseException() {

    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(CodeEnum code) {
        super(code.getMsg());
        this.code = code;
    }

    public CodeEnum getCode() {
        return code;
    }
}
