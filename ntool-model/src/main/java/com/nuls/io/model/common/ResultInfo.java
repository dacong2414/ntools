/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * @author hhu
 * @version $Id: ResultInfo.java, v 0.1 2017年5月15日 上午11:50:55 hhu Exp $
 */
@ApiModel(value = "消息实体")
public class ResultInfo<T> implements Serializable {

    /** */
    private static final long serialVersionUID = 6666515684668344232L;
    @ApiModelProperty(value = "消息编码", required = true)
    private Long              code;
    @ApiModelProperty(value = "数据", required = true)
    private T                 data;
    @ApiModelProperty(value = "返回消息")
    private String            msg;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccessResult(T data) {
        this.msg = CodeEnum.SUCCESS.getMsg();
        this.data = data;
        code = CodeEnum.SUCCESS.getCode();
    }

    public static <T> ResultInfo<T> createSuccessResult(T data) {
        ResultInfo<T> ret = new ResultInfo<T>();
        ret.msg = CodeEnum.SUCCESS.getMsg();
        ret.data = data;
        ret.code = CodeEnum.SUCCESS.getCode();
        return ret;
    }

    public static <T> ResultInfo<T> createResult(CodeEnum codeEnum, T data) {
        ResultInfo<T> ret = new ResultInfo<T>();
        ret.msg = codeEnum.getMsg();
        ret.data = data;
        ret.code = codeEnum.getCode();
        return ret;
    }

    public static <T> ResultInfo<T> createResult(CodeEnum codeEnum) {
        ResultInfo<T> ret = new ResultInfo<T>();
        ret.msg = codeEnum.getMsg();
        ret.code = codeEnum.getCode();
        return ret;
    }
}
