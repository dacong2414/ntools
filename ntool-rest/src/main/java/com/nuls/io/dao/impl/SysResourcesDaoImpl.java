package com.nuls.io.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nuls.io.dao.MysqlDaoSupport;
import com.nuls.io.dao.SysResourcesDao;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.SysResources;

/**
 * 
 * 资源数据相关操作实现
 * @author wxh
 * @version $Id: SysResourcesDaoImpl.java, v 0.1 2017年8月22日 下午2:17:45 ambition Exp $
 */
@Repository
public class SysResourcesDaoImpl extends MysqlDaoSupport implements SysResourcesDao {
    /** 
     * @param uId
     * @return
     * @see com.nuls.io.dao.SysResourcesDao#getUmstModuleResourceByUID(java.lang.Long)
     */
    @Override
    public List<SysResources> getUmstModuleResourceByUID(Long uId) {
        String sql = "SELECT sre.* FROM user_info ui, sys_users_roles sur, sys_roles sr, sys_roles_resources srr, sys_resources sre WHERE ui.u_id = sur.user_id AND sur.role_id = sr.role_id AND srr.role_id = sr.role_id AND srr.resource_id = sre.resource_id AND sr.use_flag = 1 AND sre.use_flag = 1 AND sre.resource_type = 'umtModules' AND ui.u_id =:u_id";
        String[] keys = { "u_id" };
        Object[] values = { uId };
        return sqlExecuteList(SysResources.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    @Override
    public SysResources getSysResourceByResourceId(Long resourceId) {
        String[] keys = { "resourceId" };
        Object[] values = { resourceId };
        return criteriaExecuteUniqueResult(SysResources.class, keys, values);
    }

    @Override
    public void deleteSysRoleResourceByResourcId(Long resourceId) {
        String sql = "DELETE FROM sys_roles_resources WHERE resource_id=:resourceId";
        String[] keys = { "resourceId" };
        Object[] values = { resourceId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public void deleteSysResourceByResourceIdAndField(Long resourceId) {
        String sql = "DELETE FROM sys_resources WHERE resource_id=:resourceId";
        String[] keys = { "resourceId" };
        Object[] values = { resourceId };
        sqlExecuteUpdate(sql, keys, values);
    }

    @Override
    public SysResources getSysResourceByResourceName(String resourceName) {
        String[] keys = { "resourceName" };
        Object[] values = { resourceName };
        return criteriaExecuteUniqueResult(SysResources.class, keys, values);
    }

    /** 
     * @param getuId
     * @return
     * @see com.nuls.io.dao.SysResourcesDao#getUrlResourcesByUid(java.lang.Long)
     */
    @Override
    public List<SysResources> getUrlResourcesByUid(Long uId) {
        String sql = "SELECT sre.* FROM user_info ui, sys_users_roles sur, sys_roles sr, sys_roles_resources srr, sys_resources sre WHERE ui.u_id = sur.user_id AND sur.role_id = sr.role_id AND srr.role_id = sr.role_id AND srr.resource_id = sre.resource_id AND sr.use_flag = 1 AND sre.use_flag = 1 AND sre.resource_type = 'url' AND ui.u_id =:u_id";
        String[] keys = { "u_id" };
        Object[] values = { uId };
        return sqlExecuteList(SysResources.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.nuls.io.dao.SysResourcesDao#getUrlResources()
     */
    @Override
    public List<SysResources> getUrlResources() {
        String sql = "SELECT * FROM sys_resources sr WHERE sr.resource_type='url'";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(SysResources.class, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }
}
