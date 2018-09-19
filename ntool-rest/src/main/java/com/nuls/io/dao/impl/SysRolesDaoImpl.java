package com.nuls.io.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nuls.io.dao.MysqlDaoSupport;
import com.nuls.io.dao.SysRolesDao;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.SysRoles;
import com.nuls.io.model.entity.SysRolesResources;
import com.nuls.io.model.entity.SysUsersRoles;

/**
 * 角色数据相关操作实现
 * @author ambition
 * @version $Id: SysRolesDaoImpl.java, v 0.1 2017年8月22日 下午2:17:45 ambition Exp $
 */
@Repository
public class SysRolesDaoImpl extends MysqlDaoSupport implements SysRolesDao {

    @Override
    public SysRoles getSysRoleByRoleId(Long roleId) {
        String[] keys = { "roleId" };
        Object[] values = { roleId };
        return criteriaExecuteUniqueResult(SysRoles.class, keys, values);
    }

    @Override
    public void deleteSysUserRole(Long uId, Long roleId) {
        String sql = "DELETE FROM sys_users_roles WHERE user_id =:uId AND role_id=:roleId";
        String[] keys = { "uId", "roleId" };
        Object[] values = { uId, roleId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public void deleteSysRoleResourceByRoleId(Long roleId) {
        String sql = "DELETE FROM sys_roles_resources WHERE role_id=:roleId";
        String[] keys = { "roleId" };
        Object[] values = { roleId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public void deleteSysUserRoleByRoleId(Long roleId) {
        String sql = "DELETE FROM sys_users_roles WHERE role_id=:roleId";
        String[] keys = { "roleId" };
        Object[] values = { roleId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public void deleteSysRole(Long roleId) {
        String sql = "DELETE FROM sys_roles WHERE role_id=:roleId ";
        String[] keys = { "roleId" };
        Object[] values = { roleId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public void deleteSysRoleResource(Long roleId, Long resourceId) {
        String sql = "DELETE FROM sys_roles_resources WHERE role_id=:roleId AND resource_id=:resourceId";
        String[] keys = { "roleId", "resourceId" };
        Object[] values = { roleId, resourceId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public SysRoles getSysRoleByRoleName(String roleName) {
        String[] keys = { "roleName" };
        Object[] values = { roleName };
        return criteriaExecuteUniqueResult(SysRoles.class, keys, values);
    }

    @Override
    public SysUsersRoles getSysUserRoleByUidAndRoleId(Long uId, Long roleId) {
        String[] keys = { "userId", "roleId" };
        Object[] values = { uId, roleId };
        return criteriaExecuteUniqueResult(SysUsersRoles.class, keys, values);
    }

    @Override
    public SysRolesResources getSysRoleResourceByRoleIdAndResourceId(Long roleId, Long resourceId) {
        String[] keys = { "roleId", "resourceId" };
        Object[] values = { roleId, resourceId };
        return criteriaExecuteUniqueResult(SysRolesResources.class, keys, values);
    }

    /** 
     * @param uId
     * @return
     * @see com.nuls.io.dao.SysRolesDao#getSysRoleByUid(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<SysRoles> getSysRoleByUid(Long uId) {
        String sql = "SELECT sr.* FROM sys_roles sr, user_info ui, sys_users_roles sur WHERE sr.role_id = sur.role_id AND sur.user_id = ui.u_id  AND ui.u_id =:u_id";
        String[] keys = { "u_id" };
        Object[] values = { uId };
        return sqlExecuteList(SysRoles.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.nuls.io.dao.SysRolesDao#getSysRoleList()
     */
    @Override
    public List<SysRoles> getSysRoleList(Long useFlag) {
        String[] keys = { "useFlag" };
        Object[] values = { useFlag };
        return criteriaExecuteList(SysRoles.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }
}
