package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * mac地址表（保存mac地址 有的话覆盖  没有就新增）
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "mac_address", schema = "ambitionj2c", catalog = "")
public class MacAddress {
    private Long   recId;
    private String userName;
    private String macAddress;
    private String zipName;
    private Date   gmtCreate;
    private Date   gmtMod;
    private String updateVersion;

    /*`rec_id`：MAC地址ID,
    `user_name` ：用户名称,
    `mac_address` ：MAC地址,
    `zip_name`：压缩文件名,
    `update_version` ：更新版本,
    `gmt_create` ：创建时间,
    `gmt_modified` ：修改时间*/
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
    @Column(name = "user_name", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "mac_address", nullable = true, length = 255)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Basic
    @Column(name = "zip_name", nullable = true, length = 1000)
    public String getZipName() {
        return zipName;
    }

    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

    @Basic
    @Column(name = "gmt_create", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_mod", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getGmtMod() {
        return gmtMod;
    }

    public void setGmtMod(Date gmtMod) {
        this.gmtMod = gmtMod;
    }

    @Basic
    @Column(name = "update_version", nullable = true, length = 255)
    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MacAddress that = (MacAddress) o;

        if (recId != that.recId)
            return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null)
            return false;
        if (macAddress != null ? !macAddress.equals(that.macAddress) : that.macAddress != null)
            return false;
        if (zipName != null ? !zipName.equals(that.zipName) : that.zipName != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
            return false;
        if (updateVersion != null ? !updateVersion.equals(that.updateVersion)
            : that.updateVersion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (macAddress != null ? macAddress.hashCode() : 0);
        result = 31 * result + (zipName != null ? zipName.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (updateVersion != null ? updateVersion.hashCode() : 0);
        return result;
    }
}
