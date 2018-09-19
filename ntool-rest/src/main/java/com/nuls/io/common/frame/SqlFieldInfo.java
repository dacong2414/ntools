/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.common.frame;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 字段属性表（从WEB-INF/config/sqlqueryinfo中的xml获取）
 *
 * @author Dale Chao
 * @version $Id: SqlFieldInfo.java, v 0.1 2016年7月28日 下午4:47:22 Dale Chao Exp $
 */
@Data
public class SqlFieldInfo implements Serializable {

    private static final long     serialVersionUID = -532639408244816901L;

    private Long                  fieldId;                                //字段ID
    private String                frameName;                              //框架列表名称
    private String                fieldName;                              //字段名
    private String                showName;                               //字段显示名
    private Long                  showIndex;
    private String                fieldType;                              //字段类型:String,date,long，..
    /*
    queryType：查询类型
        0.表示不提供查询；
        1.精确查询 (value=某值)
        2.模糊查询(value like %某值%)
        3.数字范围查询(5<=value<=10)
        4.时间日期查询(value=2012-10-11)
        5.时间范围查询 (如：2012-10-31<=value<=2012-12-25)
        6.下拉列表查询
     */
    private Long                  queryType;
    private Long                  csId;                                   //常量ID
    //保存数据
    private Object                data;
    //保存queryType=6时的下拉列表
    private List<SqlConstantInfo> constantInfos;

    /**
     * Getter method for property <tt>fieldId</tt>.
     * 
     * @return property value of fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * Setter method for property <tt>fieldId</tt>.
     * 
     * @param fieldId value to be assigned to property fieldId
     */
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
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
     * Getter method for property <tt>fieldName</tt>.
     * 
     * @return property value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter method for property <tt>fieldName</tt>.
     * 
     * @param fieldName value to be assigned to property fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter method for property <tt>showName</tt>.
     * 
     * @return property value of showName
     */
    public String getShowName() {
        return showName;
    }

    /**
     * Setter method for property <tt>showName</tt>.
     * 
     * @param showName value to be assigned to property showName
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * Getter method for property <tt>showIndex</tt>.
     * 
     * @return property value of showIndex
     */
    public Long getShowIndex() {
        return showIndex;
    }

    /**
     * Setter method for property <tt>showIndex</tt>.
     * 
     * @param showIndex value to be assigned to property showIndex
     */
    public void setShowIndex(Long showIndex) {
        this.showIndex = showIndex;
    }

    /**
     * Getter method for property <tt>fieldType</tt>.
     * 
     * @return property value of fieldType
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * Setter method for property <tt>fieldType</tt>.
     * 
     * @param fieldType value to be assigned to property fieldType
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Getter method for property <tt>queryType</tt>.
     * 
     * @return property value of queryType
     */
    public Long getQueryType() {
        return queryType;
    }

    /**
     * Setter method for property <tt>queryType</tt>.
     * 
     * @param queryType value to be assigned to property queryType
     */
    public void setQueryType(Long queryType) {
        this.queryType = queryType;
    }

    /**
     * Getter method for property <tt>csId</tt>.
     * 
     * @return property value of csId
     */
    public Long getCsId() {
        return csId;
    }

    /**
     * Setter method for property <tt>csId</tt>.
     * 
     * @param csId value to be assigned to property csId
     */
    public void setCsId(Long csId) {
        this.csId = csId;
    }

    /**
     * Getter method for property <tt>data</tt>.
     * 
     * @return property value of data
     */
    public Object getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     * 
     * @param data value to be assigned to property data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Getter method for property <tt>constantInfos</tt>.
     * 
     * @return property value of constantInfos
     */
    public List<SqlConstantInfo> getConstantInfos() {
        return constantInfos;
    }

    /**
     * Setter method for property <tt>constantInfos</tt>.
     * 
     * @param constantInfos value to be assigned to property constantInfos
     */
    public void setConstantInfos(List<SqlConstantInfo> constantInfos) {
        this.constantInfos = constantInfos;
    }

}
