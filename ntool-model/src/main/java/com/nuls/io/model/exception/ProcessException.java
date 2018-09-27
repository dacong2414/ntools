/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.exception;

import com.nuls.io.model.common.CodeEnum;

public class ProcessException extends BaseException {

    /** */
    private static final long serialVersionUID = -2148009949107052123L;

    public ProcessException(CodeEnum code) {
        super(code);
    }
}
