/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.model.exception;

import com.nuls.io.model.common.CodeEnum;

/**
 * 
 * 回滚异常（可不显式处理） service层主动抛出此异常会回滚事务（所有继承自RuntimeException都会回滚）
 * 
 * @author chaoyuhang
 * @version $Id: RcsRollbackException.java, v 0.1 2014年6月27日 下午3:43:30 chaoyuhang Exp $
 */
public class RcsssRollbackException extends BaseException {
    /**
     * 类序列化
     */
    private static final long serialVersionUID = 780853969785292212L;

    public RcsssRollbackException(CodeEnum code) {
        super(code);
    }
}
