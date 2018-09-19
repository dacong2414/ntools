package com.nuls.io.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Range;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.exception.ProcessException;

/**
 * 客户端
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "client_info", schema = "ambitionj2c", catalog = "")
public class ClientInfo {
    private Long                 clientId;
    private String               clientName;
    private String               macAddress;
    private List<ClientProperty> listClientProperty;

    /*client_id ：客户端id
    client_name：客户端名称
    mac_address：mac地址*/
    @Id
    @Column(name = "client_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Transient
    @ApiModelProperty(hidden = false)
    public List<ClientProperty> getListClientProperty() {
        return listClientProperty;
    }

    public void setListClientProperty(List<ClientProperty> listClientProperty) {
        this.listClientProperty = listClientProperty;
    }

    @Basic
    @Column(name = "client_name", nullable = true, length = 255)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Basic
    @Column(name = "mac_address", nullable = true, length = 255)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientInfo that = (ClientInfo) o;

        if (clientId != that.clientId)
            return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null)
            return false;
        if (macAddress != null ? !macAddress.equals(that.macAddress) : that.macAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (macAddress != null ? macAddress.hashCode() : 0);
        return result;
    }

    /**
     * 校验信息
     */
    public void check() {
        if (macAddress == null) {
            throw new ProcessException(CodeEnum.ERROR_5027);
        }
        if (!Range.openClosed(16, 17).contains(macAddress.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5011);
        }
        if (clientName == null) {
            throw new ProcessException(CodeEnum.ERROR_5028);
        }
        if (!Range.openClosed(0, 20).contains(clientName.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5012);
        }
    }
}
