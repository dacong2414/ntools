package com.nuls.io.model.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.collect.Range;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.exception.ProcessException;

/**
 * 分组
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "group_info", schema = "ambitionj2c", catalog = "")
public class GroupInfo {
    private Long               groupId;
    private String             content;
    private Long               fGroupId;
    private String             groupName;
    private String             controlFlag;
    private Date               gmtCreate;
    private Date               gmtMod;
    /**
     * 所有人可见   8
     */
    public static final String ALL_PERSON_SEE          = "allPersonSee";
    /**
     * 所有人不可见  8
     */
    public static final String ALL_PERSON_NO_SEE       = "allPersonNoSee";
    /**
     * 同组可见递归下级  6
     */
    public static final String SAME_GROUP_RECURSION    = "sameGroupRecursion";
    /** 
     * 同组可见不递归下级  6
     */
    public static final String SAME_GROUP_NO_RECURSION = "sameGroupNoRecursion";

    /*`group_id`：分组ID,
    `content` ：分组说明,
    `f_group_id`：上级分组ID,
    `group_name`：分组名称,
     controlFlag ：所有人可见、同组可见，不递归下级、同组可见递归下级、所有人可见
    `gmt_create` ：创建时间,
    `gmt_modified` ：修改时间*/
    @Id
    @Column(name = "group_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "control_flag", nullable = true, length = 255)
    public String getControlFlag() {
        return controlFlag;
    }

    /**
     * Setter method for property <tt>controlFlag</tt>.
     * 
     * @param controlFlag value to be assigned to property controlFlag
     */
    public void setControlFlag(String controlFlag) {
        this.controlFlag = controlFlag;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 2000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "f_group_id", nullable = true)
    public Long getfGroupId() {
        return fGroupId;
    }

    public void setfGroupId(Long fGroupId) {
        this.fGroupId = fGroupId;
    }

    @Basic
    @Column(name = "group_name", nullable = true, length = 255)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GroupInfo groupInfo = (GroupInfo) o;

        if (groupId != groupInfo.groupId)
            return false;
        if (content != null ? !content.equals(groupInfo.content) : groupInfo.content != null)
            return false;
        if (fGroupId != null ? !fGroupId.equals(groupInfo.fGroupId) : groupInfo.fGroupId != null)
            return false;

        if (groupName != null ? !groupName.equals(groupInfo.groupName)
            : groupInfo.groupName != null)
            return false;
        if (gmtCreate != null ? !gmtCreate.equals(groupInfo.gmtCreate)
            : groupInfo.gmtCreate != null)
            return false;
        if (gmtMod != null ? !gmtMod.equals(groupInfo.gmtMod) : groupInfo.gmtMod != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (fGroupId != null ? fGroupId.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtMod != null ? gmtMod.hashCode() : 0);
        return result;
    }

    /**
     *
     */
    public void check() {
        if (groupName == null) {
            throw new ProcessException(CodeEnum.ERROR_5029);
        }
        if (!Range.openClosed(0, 20).contains(groupName.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5019);
        }
        if (content == null) {
            throw new ProcessException(CodeEnum.ERROR_5030);
        }
        if (!Range.openClosed(0, 200).contains(content.trim().length())) {
            throw new ProcessException(CodeEnum.ERROR_5020);
        }
    }
}
