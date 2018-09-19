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
 * 自动更新表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "update_config", schema = "ambitionj2c", catalog = "")
public class UpdateConfig {
    private Long   recId;
    private String updateVersion;
    private String updatePath;
    private String updateType;
    private Date   gmtCreate;
    private Date   gmtMod;
    private Long   forceType;
    private String sysInfo;

    /* `rec_id`：更新配置ID,
     `update_version` ：更新版本,
     `update_path` ：更新路径,
     `update_type`：更新类型,
     `force_type` ：是否强制更新,
     `sys_info` ：系统信息,
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
    @Column(name = "update_version", nullable = false, length = 255)
    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    @Basic
    @Column(name = "update_path", nullable = false, length = 255)
    public String getUpdatePath() {
        return updatePath;
    }

    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

    @Basic
    @Column(name = "update_type", nullable = true, length = 255)
    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
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
    @Column(name = "force_type", nullable = true)
    public Long getForceType() {
        return forceType;
    }

    public void setForceType(Long forceType) {
        this.forceType = forceType;
    }

    @Basic
    @Column(name = "sys_info", nullable = true, length = 255)
    public String getSysInfo() {
        return sysInfo;
    }

    public void setSysInfo(String sysInfo) {
        this.sysInfo = sysInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UpdateConfig that = (UpdateConfig) o;

        if (recId != that.recId)
            return false;
        if (updateVersion != null ? !updateVersion.equals(that.updateVersion)
            : that.updateVersion != null)
            return false;
        if (updatePath != null ? !updatePath.equals(that.updatePath) : that.updatePath != null)
            return false;
        if (updateType != null ? !updateType.equals(that.updateType) : that.updateType != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(that.gmtMod) : that.gmtMod != null)
            return false;
        if (forceType != null ? !forceType.equals(that.forceType) : that.forceType != null)
            return false;
        if (sysInfo != null ? !sysInfo.equals(that.sysInfo) : that.sysInfo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (recId ^ (recId >>> 32));
        result = 31 * result + (updateVersion != null ? updateVersion.hashCode() : 0);
        result = 31 * result + (updatePath != null ? updatePath.hashCode() : 0);
        result = 31 * result + (updateType != null ? updateType.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        result = 31 * result + (forceType != null ? forceType.hashCode() : 0);
        result = 31 * result + (sysInfo != null ? sysInfo.hashCode() : 0);
        return result;
    }
}
