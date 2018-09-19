package com.nuls.io.service;

import com.nuls.io.model.entity.SysRoles;

import java.util.List;

/**
 * 角色服务
 * @author wxh
 */
public interface SysRolesService {

    /**
     * 新增角色
     * @param sysRoles 角色对象
     */
    void insertSysRole(SysRoles sysRoles);

    /**
     *  修改角色
     * @param sysRoles 角色对象
     */
    void updateSysRole(SysRoles sysRoles);

    /**
     *  根据角色ID获取单条角色信息
     * @param roleId 角色ID
     * @return SysRoles 角色信息对象
     */
    SysRoles getSysRoleByRoleId(Long roleId);

    /**
     *  新增用户角色关系信息
     * @param uId 用户ID
     * @param roleId 角色ID
     */
    Long insertSysUserRole(Long uId, Long roleId);

    /**
     *  根据用户ID和角色ID删除用户角色关系信息
     * @param uId 用户ID
     * @param roleId 角色ID
     */
    void deleteSysUserRole(Long uId, Long roleId);

    /**
     *  根据角色ID删除单条角色信息(角色资源关系、用户角色关系、角色相关信息)
     * @param roleId 角色ID
     */
    void deleteSysRole(Long roleId);

    /**
     *  新增角色资源关系信息
     * @param roleId 角色ID
     * @param resourceId 资源ID
     */
    Long insertSysRoleResource(Long roleId, Long resourceId);

    /**
     *  根据角色ID和资源ID删除角色资源关系信息
     * @param roleId 角色ID
     * @param resourceId 资源ID
     */
    void deleteSysRoleResource(Long roleId, Long resourceId);

    /**
     *  校验角色名称是否存在
     * @param roleName 角色名称
     * @param  roleId 角色ID
     * @return boolean 布尔类型(存在或不存在0
     */
    boolean checkRoleName(String roleName,Long roleId);

    /**
     * 获取所有角色（新增用户时选择）
     * @return
     */
    List<SysRoles> getSysRoleList();
}
