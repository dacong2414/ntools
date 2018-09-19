package com.nuls.io.service;

import java.util.List;

import com.nuls.io.model.entity.SysResources;

/**
 * 资源服务
 * @author wxh
 */
public interface SysResourcesService {

    /**
     * 新增资源
     * @param sysResources 资源对象
     */
    void insertSysResource(SysResources sysResources);

    /**
     * 修改资源
     * @param sysResources 资源对象
     */
    void updateSysResource(SysResources sysResources);

    /**
     *  根据资源ID获取单条资源信息
     * @param resourceId 资源ID
     * @return SysResources 资源信息对象
     */
    SysResources getSysResourceByResourceId(Long resourceId);

    /**
     *  根据资源ID删除单条资源信息
     * @param resourceId 资源ID
     */
    void deleteSysResource(Long resourceId);

    /**
     *  校验资源名称是否存在
     * @param resourceName 资源名称
     * @param resourceId 资源ID
     * @return boolean 布尔类型(存在或不存在)
     */
    boolean checkResourceName(String resourceName, Long resourceId);

    /**
     *获取所有需要权限验证的url资源
     * @return
     */
    List<SysResources> getAllUrlResources();

}
