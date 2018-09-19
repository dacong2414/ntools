/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.model.exception;

import com.nuls.io.model.common.CodeEnum;

/**
 * 权限异常（可不显式处理）
 *
 * @author chaoyuhang
 * @version $Id: RcsSecurityException.java, v 0.1 2014年6月27日 下午3:43:41 chaoyuhang Exp $
 */
public class RcsssSecurityException extends BaseException {
    /**
     * 类序列化
     */
    private static final long serialVersionUID = 6528006596983319791L;

    public RcsssSecurityException(CodeEnum code) {
        super(code);
    }
}
