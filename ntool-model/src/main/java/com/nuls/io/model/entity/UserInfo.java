package com.nuls.io.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "user_info", schema = "ambitionj2c", catalog = "")
@JSONType(orders = { "uId", "title", "description" }, ignores = { "email", "disableFlag",
                                                                 "userType", "gmtCreate", "gmtMod",
                                                                 "auths" })
public class UserInfo {
    private Long             uId;
    private String           title;
    private String           description;
    private String           email;
    private Long             disableFlag;
    private Long             userType;
    private Date             gmtCreate;
    private Date             gmtMod;
    /* `u_id`：用户ID,
     `title` ：用户中文名,
     `description`：用户描述,
    `disable_flag`：是否失效0.失效 1.有效，
     `email`：用户邮箱,
     `user-type`：用户类型,
     `gmt_create` ：创建时间,
     `gmt_modified` ：修改时间
    'is_sys':是否是超级管理员*/
    /**
     * 普通用户
     */
    public static final Long USERTYPE_COMMON_USER = 0L;
    /**
     * 管理员
     */
    public static final Long USERTYPE_ADMIN_USER  = 1L;
    //数据库无关字段
    private Set<String>      auths                = new HashSet<String>(); //用户service权限组

    @Transient
    @ApiModelProperty(hidden = true)
    public Set<String> getAuths() {
        return auths;
    }

    public void setAuths(Set<String> auths) {
        this.auths = auths;
    }

    @Id
    @Column(name = "u_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    /**
     * Getter method for property <tt>isSys</tt>.
     * 
     * @return property value of isSys
     */
    @Basic
    @Column(name = "user_Type", nullable = false)
    public Long getUserType() {
        return userType;
    }

    /**
     * Setter method for property <tt>isSys</tt>.
     * 
     * @param isSys value to be assigned to property isSys
     */
    public void setUserType(Long userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        UserInfo userInfo = (UserInfo) o;

        if (uId != userInfo.uId)
            return false;
        if (disableFlag != userInfo.disableFlag)
            return false;
        if (title != null ? !title.equals(userInfo.title) : userInfo.title != null)
            return false;
        if (description != null ? !description.equals(userInfo.description)
            : userInfo.description != null)
            return false;
        if (email != null ? !email.equals(userInfo.email) : userInfo.email != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(userInfo.gmtCreate) : userInfo.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(userInfo.gmtMod) : userInfo.gmtMod != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (uId ^ (uId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (int) (disableFlag ^ (disableFlag >>> 32));
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        return result;
    }
}
