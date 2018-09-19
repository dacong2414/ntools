package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * mac地址表和登录名的映射表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "mac_address_login_name", schema = "ambitionj2c", catalog = "")
public class MacAddressLoginName {
    private Long   recId;
    private String loginName;
    private String macAddress;

    /*`rec_id`：MAC地址ID,
    `login_name` ：登录名称,
    `mac_address` ：MAC地址,
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
    @Column(name = "login_name", nullable = true, length = 255)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "mac_address", nullable = true, length = 255)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

}
