package com.nuls.io.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 登录表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "logon_info", schema = "ambitionj2c", catalog = "")
public class LogonInfo {
    private Long               recId;
    private Long               uId;
    private String             loginName;
    private String             loginPwd;
    private Long               disableFlag;
    private Date               gmtCreate;
    private Date               gmtMod;
    private UserInfo           userInfo;
    private List<SysRoles>     listSysRoles;
    private List<SysResources> listSysResources;

    /* `rec_id`：主键ID,  
     `u_id`：用户ID,
     `login_name` ：登录名,
     `login_pwd`：密码,
     `disable_flag`：是否失效0.失效 1.有效，
     `gmt_create` ：创建时间,
     `gmt_modified` ：修改时间*/

    @Transient
    @ApiModelProperty(hidden = true)
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Transient
    @ApiModelProperty(hidden = true)
    public List<SysRoles> getListSysRoles() {
        return listSysRoles;
    }

    public void setListSysRoles(List<SysRoles> listSysRoles) {
        this.listSysRoles = listSysRoles;
    }

    @Transient
    @ApiModelProperty(hidden = true)
    public List<SysResources> getListSysResources() {
        return listSysResources;
    }

    /**
     * Setter method for property <tt>listSysResources</tt>.
     * 
     * @param listSysResources value to be assigned to property listSysResources
     */
    public void setListSysResources(List<SysResources> listSysResources) {
        this.listSysResources = listSysResources;
    }

    @Id
    @Column(name = "rec_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "u_id", nullable = false)
    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    @Basic
    @Column(name = "login_name", nullable = false, length = 255)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "login_pwd", nullable = false, length = 255)
    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    @Basic
    @Column(name = "disable_flag", nullable = false)
    public Long getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Long disableFlag) {
        this.disableFlag = disableFlag;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LogonInfo logonInfo = (LogonInfo) o;

        if (uId != logonInfo.uId)
            return false;
        if (disableFlag != logonInfo.disableFlag)
            return false;
        if (loginName != null ? !loginName.equals(logonInfo.loginName)
            : logonInfo.loginName != null)
            return false;
        if (loginPwd != null ? !loginPwd.equals(logonInfo.loginPwd) : logonInfo.loginPwd != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(logonInfo.gmtCreate)
            : logonInfo.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(logonInfo.gmtMod) : logonInfo.gmtMod != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (uId ^ (uId >>> 32));
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (loginPwd != null ? loginPwd.hashCode() : 0);
        result = 31 * result + (int) (disableFlag ^ (disableFlag >>> 32));
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        return result;
    }

}
