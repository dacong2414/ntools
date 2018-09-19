package com.nuls.io.model.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 联动规则
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "regular_linkage", schema = "ambitionj2c", catalog = "")
public class RegularLinkage {
    private Long         functionId;
    private Long         propertyId;
    private String       propertyValue;
    private Long         fFunctionId;
    private PropertyInfo propertyInfo;

    /*function_id ：规则id
    property_id：属性ID
    property_value：属性值
    f_function_id：触发规则ID，0表示当前属性是父节点，是触发条件，其他ID表示当前属性子节点，是执行联动，且ID是父节点的ID
    */
    @Id
    @Column(name = "function_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    @Transient
    @ApiModelProperty(hidden = true)
    public PropertyInfo getPropertyInfo() {
        return propertyInfo;
    }

    public void setPropertyInfo(PropertyInfo propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    @Basic
    @Column(name = "property_id", nullable = false)
    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    @Basic
    @Column(name = "property_value", nullable = true, length = 255)
    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Basic
    @Column(name = "f_function_id", nullable = false)
    public Long getfFunctionId() {
        return fFunctionId;
    }

    public void setfFunctionId(Long fFunctionId) {
        this.fFunctionId = fFunctionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RegularLinkage that = (RegularLinkage) o;

        if (functionId != that.functionId)
            return false;
        if (propertyId != that.propertyId)
            return false;
        if (fFunctionId != that.fFunctionId)
            return false;
        if (propertyValue != null ? !propertyValue.equals(that.propertyValue)
            : that.propertyValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (functionId ^ (functionId >>> 32));
        result = 31 * result + (int) (propertyId ^ (propertyId >>> 32));
        result = 31 * result + (propertyValue != null ? propertyValue.hashCode() : 0);
        result = 31 * result + (int) (fFunctionId ^ (fFunctionId >>> 32));
        return result;
    }
}
