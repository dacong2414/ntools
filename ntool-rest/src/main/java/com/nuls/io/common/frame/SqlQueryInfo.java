package com.nuls.io.common.frame;

/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * SQL语句表（从WEB-INF/config/sqlqueryinfo中的xml获取）
 *
 * @author Dale Chao
 * @version $Id: SqlQueryInfo.java, v 0.1 2016年7月28日 下午5:27:16 Dale Chao Exp $
 */
@Data
public class SqlQueryInfo implements Serializable {

    private static final long  serialVersionUID = -6376923370096366419L;

    private String             frameName;                               //查询框架名称
    private String             sqlDetial;                               //查询SQL主体
    private String             sqlTemp;                                 //出数据所需的模版：0.默认通用模版；其他数字表示模版id
    private String             sqlTitle;                                //sql标题
    private String             sqlInfo;                                 //sql说明
    private String             extendWidth      = "auto";               //操作栏宽度

    private List<SqlFieldInfo> fieldInfos       = new ArrayList<>();

    /**
     * Setter method for property <tt>extendWidth</tt>.
     * 
     * @param extendWidth value to be assigned to property extendWidth
     */
    public void setExtendWidth(String extendWidth) {
        if (extendWidth != null && !extendWidth.equals("")) {
            this.extendWidth = extendWidth;
        }
    }

    /**
     * Getter method for property <tt>frameName</tt>.
     * 
     * @return property value of frameName
     */
    public String getFrameName() {
        return frameName;
    }

    /**
     * Setter method for property <tt>frameName</tt>.
     * 
     * @param frameName value to be assigned to property frameName
     */
    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    /**
     * Getter method for property <tt>sqlDetial</tt>.
     * 
     * @return property value of sqlDetial
     */
    public String getSqlDetial() {
        return sqlDetial;
    }

    /**
     * Setter method for property <tt>sqlDetial</tt>.
     * 
     * @param sqlDetial value to be assigned to property sqlDetial
     */
    public void setSqlDetial(String sqlDetial) {
        this.sqlDetial = sqlDetial;
    }

    /**
     * Getter method for property <tt>sqlTemp</tt>.
     * 
     * @return property value of sqlTemp
     */
    public String getSqlTemp() {
        return sqlTemp;
    }

    /**
     * Setter method for property <tt>sqlTemp</tt>.
     * 
     * @param sqlTemp value to be assigned to property sqlTemp
     */
    public void setSqlTemp(String sqlTemp) {
        this.sqlTemp = sqlTemp;
    }

    /**
     * Getter method for property <tt>sqlTitle</tt>.
     * 
     * @return property value of sqlTitle
     */
    public String getSqlTitle() {
        return sqlTitle;
    }

    /**
     * Setter method for property <tt>sqlTitle</tt>.
     * 
     * @param sqlTitle value to be assigned to property sqlTitle
     */
    public void setSqlTitle(String sqlTitle) {
        this.sqlTitle = sqlTitle;
    }

    /**
     * Getter method for property <tt>sqlInfo</tt>.
     * 
     * @return property value of sqlInfo
     */
    public String getSqlInfo() {
        return sqlInfo;
    }

    /**
     * Setter method for property <tt>sqlInfo</tt>.
     * 
     * @param sqlInfo value to be assigned to property sqlInfo
     */
    public void setSqlInfo(String sqlInfo) {
        this.sqlInfo = sqlInfo;
    }

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
     * Getter method for property <tt>extendWidth</tt>.
     * 
     * @return property value of extendWidth
     */
    public String getExtendWidth() {
        return extendWidth;
    }

}
