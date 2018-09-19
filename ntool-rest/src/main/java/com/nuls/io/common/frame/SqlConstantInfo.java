/**
 * Ambition Inc.
 * Copyright (c) 2006-2012 All Rights Reserved.
 */
package com.nuls.io.common.frame;

import java.io.Serializable;

import lombok.Data;

/**
 * 框架列表常量Modal
 */
@Data
public class SqlConstantInfo implements Serializable {

    private static final long serialVersionUID = -4264045269409630693L;

    private Long              recId;
    private Long              csId;
    private String            csName;
    private String            csValue;

    /**
     * Getter method for property <tt>recId</tt>.
     * 
     * @return property value of recId
     */
    public Long getRecId() {
        return recId;
    }

    /**
     * Setter method for property <tt>recId</tt>.
     * 
     * @param recId value to be assigned to property recId
     */
    public void setRecId(Long recId) {
        this.recId = recId;
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
     * Getter method for property <tt>csName</tt>.
     * 
     * @return property value of csName
     */
    public String getCsName() {
        return csName;
    }

    /**
     * Setter method for property <tt>csName</tt>.
     * 
     * @param csName value to be assigned to property csName
     */
    public void setCsName(String csName) {
        this.csName = csName;
    }

    /**
     * Getter method for property <tt>csValue</tt>.
     * 
     * @return property value of csValue
     */
    public String getCsValue() {
        return csValue;
    }

    /**
     * Setter method for property <tt>csValue</tt>.
     * 
     * @param csValue value to be assigned to property csValue
     */
    public void setCsValue(String csValue) {
        this.csValue = csValue;
    }

}
