/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.common.frame;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 保存每行数据的POJO
 *
 * @author chaoyuhang
 * @version $Id: SqlRowData.java, v 0.1 2015年5月7日 下午2:42:57 chaoyuhang Exp $
 */
@Data
public class SqlRowData implements Serializable {

    /**
     * 序列化 
     */
    private static final long  serialVersionUID = -9132580309169104357L;
    /**
     * 每行数据
     */
    private List<SqlFieldInfo> fieldInfos;

    /**
     * 分页数据
     */
    private Page               page;
    /**
     * SQL语句表
     */
    private SqlQueryInfo       queryInfo;

    /**
     * Getter method for property <tt>fieldInfos</tt>.
     * 
     * @return property value of fieldInfos
     */
    public List<SqlFieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    /**
     * Setter method for property <tt>fieldInfos</tt>.
     * 
     * @param fieldInfos value to be assigned to property fieldInfos
     */
    public void setFieldInfos(List<SqlFieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    /**
     * Getter method for property <tt>page</tt>.
     * 
     * @return property value of page
     */
    public Page getPage() {
        return page;
    }

    /**
     * Setter method for property <tt>page</tt>.
     * 
     * @param page value to be assigned to property page
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * Getter method for property <tt>queryInfo</tt>.
     * 
     * @return property value of queryInfo
     */
    public SqlQueryInfo getQueryInfo() {
        return queryInfo;
    }

    /**
     * Setter method for property <tt>queryInfo</tt>.
     * 
     * @param queryInfo value to be assigned to property queryInfo
     */
    public void setQueryInfo(SqlQueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

}
