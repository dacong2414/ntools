package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户端属性表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "client_property", schema = "ambitionj2c", catalog = "")
public class ClientProperty {
    private Long   recId;
    private Long   clientId;
    private Long   propertyId;
    private String propertyValue;

    /*rec_id：主键id
    client_id：客户端id
    property_id：属性id
    property_value：属性值*/

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
    @Column(name = "client_id", nullable = false)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientProperty that = (ClientProperty) o;

        if (recId != that.recId)
            return false;
        if (clientId != that.clientId)
            return false;
        if (propertyId != that.propertyId)
            return false;
        if (propertyValue != null ? !propertyValue.equals(that.propertyValue)
            : that.propertyValue != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (int) (propertyId ^ (propertyId >>> 32));
        result = 31 * result + (propertyValue != null ? propertyValue.hashCode() : 0);
        return result;
    }
}
