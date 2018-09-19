package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 组的自定义关系表   7
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "group_custom_relational", schema = "ambitionj2c", catalog = "")
public class GroupCustomRelational {
    private Long               recId;
    private Long               leftId;
    private String             leftType;
    private Long               rightId;
    private String             rightType;
    private String             recursive;
    private Date               gmtCreate;
    private Date               gmtMod;
    /**
     * 左边对应类型uId
     */
    public static final String LEFT_TYPE_UID      = "uId";
    /**
     * 左边对应类型groupId
     */
    public static final String LEFT_TYPE_GROUPID  = "groupId";
    /**
     * 右边对应类型
     */
    public static final String RIGHT_TYPE_UID     = "uId";
    /**
     * 右边对应类型
     */
    public static final String RIGHT_TYPE_GROUPID = "groupId";
    /**
     * 递归下级
     */
    public static final String RECURSION          = "recursion";
    /**
     * 不递归下级
     */
    public static final String NO_RECURSION       = "noRecursion";

    /*`recId`：主键ID,
    `left` ：左边对应,
    `leftType`：左边对应类型,
    `right`：右边对应,
    `rightType`：右边对应类型,
     recursive ：是否递归
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
    @Column(name = "left_type", length = 255)
    public String getLeftType() {
        return leftType;
    }

    public void setLeftType(String leftType) {
        this.leftType = leftType;
    }

    @Basic
    @Column(name = "left_id")
    public Long getLeftId() {
        return leftId;
    }

    public void setLeftId(Long leftId) {
        this.leftId = leftId;
    }

    @Basic
    @Column(name = "right_id")
    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }

    @Basic
    @Column(name = "right_type", length = 255)
    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    @Basic
    @Column(name = "recursive", length = 255)
    public String getRecursive() {
        return recursive;
    }

    public void setRecursive(String recursive) {
        this.recursive = recursive;
    }

    @Basic
    @Column(name = "gmt_create", nullable = true)
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_mod", nullable = true)
    public Date getGmtMod() {
        return gmtMod;
    }

    public void setGmtMod(Date gmtMod) {
        this.gmtMod = gmtMod;
    }

}
