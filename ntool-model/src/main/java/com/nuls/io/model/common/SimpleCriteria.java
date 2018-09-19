package com.nuls.io.model.common;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 构造简单的查询对象
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.common, v 0.1 2017/11/1 15:55 hhu Exp $$
 */
@Data
@NoArgsConstructor
public class SimpleCriteria<T> {
    public List<String> getKey() {
		return key;
	}

	public void setKey(List<String> key) {
		this.key = key;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public List<OperatorEnum> getOperator() {
		return operator;
	}

	public void setOperator(List<OperatorEnum> operator) {
		this.operator = operator;
	}

	public Class getPerClass() {
		return perClass;
	}

	public void setPerClass(Class perClass) {
		this.perClass = perClass;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
     * 键集合
     */
    List<String>       key;
    /**
     * 值集合
     */
    List<Object>       value;
    /**
     * 操作符集合
     */
    List<OperatorEnum> operator;
    /**
     * 查询实体类
     */
    private Class      perClass;
    /**
     * 页码
     */
    private int        pageIndex;
    /**
     * 当前页查询条数
     */
    private int        pageSize;
    /**
     * 总数
     */
    private long       total;
    /**
     * 查询结果
     */
    private List<T>    result;

    public SimpleCriteria(Class perClass) {
        this.perClass = perClass;
    }
}