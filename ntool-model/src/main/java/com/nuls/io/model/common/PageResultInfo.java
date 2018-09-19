package com.nuls.io.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.model.common, v 0.1 2017/11/17 14:29 hhu Exp $$
 */
@Data
@ToString
@ApiModel(value = "消息实体")
public class PageResultInfo<T> implements Serializable {
    private static final long serialVersionUID = 6666515684668344232L;
    @ApiModelProperty(value = "消息编码", required = true)
    private Long              code;
    @ApiModelProperty(value = "数据", required = true)
    private List<T>           data;
    @ApiModelProperty(value = "返回消息")
    private String            msg;
    @ApiModelProperty(value = "当前页码")
    private Integer           pageIndex;
    @ApiModelProperty(value = "分页条数")
    private Integer           pageSize;
    @ApiModelProperty(value = "总数")
    private Long              total;

    public static <T> PageResultInfo<T> createSuccessResult(List<T> data) {
        PageResultInfo<T> ret = new PageResultInfo<>();
        ret.msg = CodeEnum.SUCCESS.getMsg();
        ret.data = data;
        ret.code = CodeEnum.SUCCESS.getCode();
        return ret;
    }

    public static <T> PageResultInfo<T> createSuccessResult(List<T> data, Integer pageIndex,
                                                            Integer pageSize, Long total) {
        PageResultInfo<T> ret = new PageResultInfo<>();
        ret.msg = CodeEnum.SUCCESS.getMsg();
        ret.data = data;
        ret.pageIndex = pageIndex;
        ret.pageSize = pageSize;
        ret.total = total;
        ret.code = CodeEnum.SUCCESS.getCode();
        return ret;
    }

    public static <T extends Serializable> PageResultInfo<T> createFailureResult() {
        PageResultInfo<T> ret = new PageResultInfo<>();
        ret.msg = CodeEnum.SUCCESS.getMsg();
        ret.code = CodeEnum.ERROR.getCode();
        return ret;
    }

    public static <T> PageResultInfo<T> createResult(CodeEnum codeEnum) {
        PageResultInfo<T> resultInfo = new PageResultInfo<>();
        resultInfo.msg = codeEnum.getMsg();
        resultInfo.code = codeEnum.getCode();
        return resultInfo;
    }
}