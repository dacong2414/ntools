package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 属性扩展表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "property_extend", schema = "ambitionj2c", catalog = "")
public class PropertyExtend {
    private Long   recId;
    private Long   propertyId;
    private String showName;
    private String showValue;

    /* rec_id：主键id
     property_id：属性id
     show_name：显示名称
     show_value：显示值
    */
    @Id
    @Column(name = "rec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
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
    @Column(name = "show_name", nullable = true, length = 255)
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Basic
    @Column(name = "show_value", nullable = true, length = 255)
    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PropertyExtend that = (PropertyExtend) o;

        if (recId != that.recId)
            return false;
        if (propertyId != that.propertyId)
            return false;
        if (showName != null ? !showName.equals(that.showName) : that.showName != null)
            return false;
        if (showValue != null ? !showValue.equals(that.showValue) : that.showValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (int) (propertyId ^ (propertyId >>> 32));
        result = 31 * result + (showName != null ? showName.hashCode() : 0);
        result = 31 * result + (showValue != null ? showValue.hashCode() : 0);
        return result;
    }
}
