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
import com.google.common.base.Strings;
import com.google.common.collect.Range;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.exception.ProcessException;

/**
 * 系统资源表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "sys_resources", schema = "ambitionj2c", catalog = "")
public class SysResources {
    private Long               resourceId;                              //资源ID
    private String             resourceType;                            //资源类型,（url或者模块对应用户模块表迁移过来umtModules）
    private String             resourceName;                            //资源名称
    private String             resourceDesc;                            //资源描述
    private String             resourcePath;                            //资源路径
    private String             priority;                                //优先级
    private Long               useFlag;                                 //使用标志0.失效 1.有效
    private Long               sysFlag;                                 //是否是系统默认资源
    private Date               gmtCreate;
    private Date               gmtMod;
    /**
     * 是系统属性，不可以更改
     */
    public static final Long   SYS_FLAG_YES              = 0L;
    /**
     * 不是系统属性，可以更改
     */
    public static final Long   SYS_FLAG_NO               = 1L;
    /**
     * 0 失效
     */
    public static final Long   USE_FLAG_NO               = 0L;
    /**
     * 1 有效
     */
    public static final Long   USE_FLAG_YES              = 1L;
    /**
     * umtModules资源
     */
    public static final String RESOURCE_TYPE_UMT_MODULES = "umtModules";
    /**
     * url资源
     */
    public static final String RESOURCE_TYPE_URL         = "url";

    @Id
    @Column(name = "resource_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    /**
     * Getter method for property <tt>sysFlag</tt>.
     * 
     * @return property value of sysFlag
     */
    @Basic
    @Column(name = "sys_flag", nullable = false)
    public Long getSysFlag() {
        return sysFlag;
    }

    /**
     * Setter method for property <tt>sysFlag</tt>.
     * 
     * @param sysFlag value to be assigned to property sysFlag
     */
    public void setSysFlag(Long sysFlag) {
        this.sysFlag = sysFlag;
    }

    @Basic
    @Column(name = "resource_type", nullable = true, length = 100)
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "resource_name", nullable = true, length = 100)
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Basic
    @Column(name = "resource_desc", nullable = true, length = 200)
    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    @Basic
    @Column(name = "resource_path", nullable = true, length = 200)
    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Basic
    @Column(name = "priority", nullable = true, length = 100)
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "use_flag", nullable = true)
    public Long getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Long useFlag) {
        this.useFlag = useFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SysResources that = (SysResources) o;

        if (resourceId != that.resourceId)
            return false;
        if (resourceType != null ? !resourceType.equals(that.resourceType)
            : that.resourceType != null)
            return false;
        if (resourceName != null ? !resourceName.equals(that.resourceName)
            : that.resourceName != null)
            return false;
        if (resourceDesc != null ? !resourceDesc.equals(that.resourceDesc)
            : that.resourceDesc != null)
            return false;
        if (resourcePath != null ? !resourcePath.equals(that.resourcePath)
            : that.resourcePath != null)
            return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null)
            return false;
        if (useFlag != null ? !useFlag.equals(that.useFlag) : that.useFlag != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (resourceId ^ (resourceId >>> 32));
        result = 31 * result + (resourceType != null ? resourceType.hashCode() : 0);
        result = 31 * result + (resourceName != null ? resourceName.hashCode() : 0);
        result = 31 * result + (resourceDesc != null ? resourceDesc.hashCode() : 0);
        result = 31 * result + (resourcePath != null ? resourcePath.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (useFlag != null ? useFlag.hashCode() : 0);
        return result;
    }

    /**
     * 资源类型字段数据校验
     */
    public void checkResourceType() {
        if (Strings.isNullOrEmpty(resourceType)) {
            throw new ProcessException(CodeEnum.ERROR_40014);
        }
        if (!Range.closedOpen(0, 100).contains(resourceType.length())) {
            throw new ProcessException(CodeEnum.ERROR_40015);
        }
    }

    /**
     * 资源名称字段数据校验
     */
    public void checkResourceName() {
        if (Strings.isNullOrEmpty(resourceName)) {
            throw new ProcessException(CodeEnum.ERROR_40016);
        }
        if (!Range.closedOpen(0, 100).contains(resourceName.length())) {
            throw new ProcessException(CodeEnum.ERROR_40017);
        }
    }

    /**
     * 资源描述字段数据校验
     */
    public void checkResourceDesc() {
        if (Strings.isNullOrEmpty(resourceDesc)) {
            throw new ProcessException(CodeEnum.ERROR_40018);
        }
        if (!Range.closedOpen(0, 200).contains(resourceDesc.length())) {
            throw new ProcessException(CodeEnum.ERROR_40019);
        }
    }

    /**
     * 资源URL字段数据校验
     */
    public void checkResourcePath() {
        if (Strings.isNullOrEmpty(resourcePath)) {
            throw new ProcessException(CodeEnum.ERROR_40020);
        }
        if (!Range.closedOpen(0, 200).contains(resourcePath.length())) {
            throw new ProcessException(CodeEnum.ERROR_40021);
        }
    }

    /**
     * 资源优先级字段数据校验
     */
    public void checkPriority() {
        if (Strings.isNullOrEmpty(priority)) {
            throw new ProcessException(CodeEnum.ERROR_40022);
        }
        if (!Range.closedOpen(0, 100).contains(priority.length())) {
            throw new ProcessException(CodeEnum.ERROR_40023);
        }
    }

    /**
     *  资源状态字段数据校验
     */
    public void checkUseFlag() {
        if (useFlag == null) {
            throw new ProcessException(CodeEnum.ERROR_40024);
        }
    }

    /**
     *  是否是管理员字段数据校验
     */
    public void checkSysFlag() {
        if (sysFlag == null) {
            throw new ProcessException(CodeEnum.ERROR_40008);
        }
    }

    /**
     * 资源信息对象数据校验
     */
    public void checkSysResource() {
        checkResourceType();
        checkResourceName();
        checkResourceDesc();
        checkResourcePath();
        checkPriority();
        checkUseFlag();
        checkSysFlag();
    }
}
