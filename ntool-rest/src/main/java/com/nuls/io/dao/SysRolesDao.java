package com.nuls.io.dao;

import java.util.List;

import com.nuls.io.model.entity.SysRoles;
import com.nuls.io.model.entity.SysRolesResources;
import com.nuls.io.model.entity.SysUsersRoles;

/**
 * 角色数据相关操作
 */
public interface SysRolesDao {

    /**
     *  根据角色ID和域ID获取角色信息
     * @param roleId 角色ID
     * @return SysRoles 角色信息对象
     */
    SysRoles getSysRoleByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID删除用户角色关系信息
     * @param uId 用户ID
     * @param roleId 角色ID
     */
    void deleteSysUserRole(Long uId, Long roleId);

    /**
     * 根据角色ID删除角色资源关系信息
     * @param roleId 角色ID
     */
    void deleteSysRoleResourceByRoleId(Long roleId);

    /**
     *  根据角色ID删除用户角色关系信息
     * @param roleId 角色ID
     */
    void deleteSysUserRoleByRoleId(Long roleId);

    /**
     *  根据角色ID和域ID删除角色信息
     * @param roleId 角色ID
     */
    void deleteSysRole(Long roleId);

    /**
     *  根据角色ID和资源ID删除角色资源关系信息
     * @param roleId 角色ID
     * @param resourceId 资源ID
     */
    void deleteSysRoleResource(Long roleId, Long resourceId);

    /**
     *  根据角色名称获取角色信息
     * @param roleName 角色名称
     * @return SysRoles 角色信息对象
     */
    SysRoles getSysRoleByRoleName(String roleName);

    /**
     *  根据用户ID和角色ID获取用户角色信息
     * @param uId 用户ID
     * @param roleId 角色ID
     * @return SysUsersRoles 用户角色关系信息
     */
    SysUsersRoles getSysUserRoleByUidAndRoleId(Long uId, Long roleId);

    /**
     *  根据角色ID和资源ID获取角色资源信息
     * @param roleId  角色ID
     * @param resourceId 資源ID
     * @return SysRolesResources 角色資源關係信息
     */
    SysRolesResources getSysRoleResourceByRoleIdAndResourceId(Long roleId, Long resourceId);

    /**
     * 获取该用户所属角色
     * @param uId
     * @return
     */
    List<SysRoles> getSysRoleByUid(Long uId);

    /**
     * 获取所有角色（用于新增用户时选择 useFlag=1）
     *
     * @param useFlag
     * @return
     */
    List<SysRoles> getSysRoleList(Long useFlag);

}
