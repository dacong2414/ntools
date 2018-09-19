package com.nuls.io.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Strings;
import com.google.common.collect.Range;
import com.nuls.io.model.common.CodeEnum;
import com.nuls.io.model.exception.ProcessException;

/**
 * 系统角色表
 * Created by ambition on 2017/7/31.
 */
@Entity
@Table(name = "sys_roles", schema = "ambitionj2c", catalog = "")
public class SysRoles {
    private Long               roleId;                             //角色id
    private String             roleName;                           //角色名称
    private String             roleDesc;                           //角色描述
    private Long               useFlag;                            //使用标志0.失效 1.有效
    private Long               roleIssys;                          //是否是管理员 0不是  1是
    private Long               sysFlag;                            //是否是系统默认角色(0是系统属性不可以更改 1.不是系统属性，可以更改)

    /**
     * 监控端
     */
    public static final String ROLE_NAME_MONITOR_DCLIENT = "院内监控端";
    /**
     * hclient端
     */
    public static final String ROLE_NAME_INNER_CLIENT    = "院内客户端";
    /**
     * 是系统属性，不可以更改
     */
    public static final Long   SYS_FLAG_YES              = 0L;
    /**
     * 不是系统属性，可以更改
     */
    public static final Long   SYS_FLAG_NO               = 1L;
    /**
     * 0.失效
     */
    public static final Long   USE_FLAG_NO               = 0L;
    /**
     * 1.有效
     */
    public static final Long   USE_FLAG_YES              = 1L;

    @Id
    @Column(name = "role_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
    @Column(name = "role_name", nullable = true, length = 100)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_desc", nullable = true, length = 200)
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Basic
    @Column(name = "use_flag", nullable = true)
    public Long getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Long useFlag) {
        this.useFlag = useFlag;
    }

    @Basic
    @Column(name = "role_issys", nullable = true)
    public Long getRoleIssys() {
        return roleIssys;
    }

    public void setRoleIssys(Long roleIssys) {
        this.roleIssys = roleIssys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SysRoles sysRoles = (SysRoles) o;

        if (roleId != sysRoles.roleId)
            return false;

        if (roleName != null ? !roleName.equals(sysRoles.roleName) : sysRoles.roleName != null)
            return false;
        if (roleDesc != null ? !roleDesc.equals(sysRoles.roleDesc) : sysRoles.roleDesc != null)
            return false;
        if (useFlag != null ? !useFlag.equals(sysRoles.useFlag) : sysRoles.useFlag != null)
            return false;
        if (roleIssys != null ? !roleIssys.equals(sysRoles.roleIssys) : sysRoles.roleIssys != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleDesc != null ? roleDesc.hashCode() : 0);
        result = 31 * result + (useFlag != null ? useFlag.hashCode() : 0);
        result = 31 * result + (roleIssys != null ? roleIssys.hashCode() : 0);
        return result;
    }

    /**
     *  角色名称字段数据校验
     */
    public void checkRoleName() {
        if (Strings.isNullOrEmpty(roleName)) {
            throw new ProcessException(CodeEnum.ERROR_40001);
        }
        if (!Range.openClosed(0, 100).contains(roleName.length())) {
            throw new ProcessException(CodeEnum.ERROR_40002);
        }
    }

    /**
     *  角色描述字段数据校验
     */
    public void checkRoleDesc() {
        if (Strings.isNullOrEmpty(roleDesc)) {
            throw new ProcessException(CodeEnum.ERROR_40004);
        }
        if (!Range.openClosed(0, 100).contains(roleDesc.length())) {
            throw new ProcessException(CodeEnum.ERROR_40005);
        }
    }

    /**
     *  角色状态字段数据校验
     */
    public void checkUseFlag() {
        if (useFlag == null) {
            throw new ProcessException(CodeEnum.ERROR_40006);
        }
    }

    /**
     *  是否是管理员字段数据校验
     */
    public void checkRoleIssys() {
        if (roleIssys == null) {
            throw new ProcessException(CodeEnum.ERROR_40007);
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
     * 角色信息对象数据校验
     */
    public void checkSysRole() {
        checkRoleName();
        checkRoleDesc();
        checkUseFlag();
        checkRoleIssys();
        checkSysFlag();
    }
}
