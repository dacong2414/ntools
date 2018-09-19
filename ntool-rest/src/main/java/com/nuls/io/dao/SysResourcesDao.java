package com.nuls.io.dao;

import java.util.List;

import com.nuls.io.model.entity.SysResources;

/**
 *  资源数据相关操作
 */
public interface SysResourcesDao {
    /**
     * 通过uId获取相应的资源模块信息
     * @param getuId
     * @return
     */
    public List<SysResources> getUmstModuleResourceByUID(Long uId);

    /**
     *  根据资源ID和域ID获取单条资源信息
     * @param resourceId 资源ID
     * @return SysResources 资源信息对象
     */
    SysResources getSysResourceByResourceId(Long resourceId);

    /**
     *  根据资源ID删除角色资源关系信息
     * @param resourceId 资源ID
     */
    void deleteSysRoleResourceByResourcId(Long resourceId);

    /**
     *  根据资源ID和域ID删除资源信息
     * @param resourceId 资源ID
     */
    void deleteSysResourceByResourceIdAndField(Long resourceId);

    /**
     *  根据资源名称获取资源信息
     * @param resourceName 资源名称
     * @return SysResources 资源信息对象
     */
    SysResources getSysResourceByResourceName(String resourceName);

    /**
     *  获取url资源 通过uId
     * @param getuId
     * @return
     */
    public List<SysResources> getUrlResourcesByUid(Long uId);

    /**
     *
     */
    public List<SysResources> getUrlResources();
}
