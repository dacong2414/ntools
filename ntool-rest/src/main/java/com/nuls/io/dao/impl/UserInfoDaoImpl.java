package com.nuls.io.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nuls.io.dao.MysqlDaoSupport;
import com.nuls.io.dao.UserInfoDao;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.SysUsersRoles;
import com.nuls.io.model.entity.UserDefined;
import com.nuls.io.model.entity.UserInfo;

/**
 * 
 *
 * @author ambition
 * @version $Id: UserInfoDaoImpl.java, v 0.1 2017年8月22日 下午2:17:45 ambition Exp $
 */
@Repository
public class UserInfoDaoImpl extends MysqlDaoSupport implements UserInfoDao {

    @Override
    public UserInfo getUserInfoByUID(Long uId) {
        String[] keys = { "uId" };
        Object[] values = { uId };
        return criteriaExecuteUniqueResult(UserInfo.class, keys, values);
    }

    @Override
    public List<SysUsersRoles> getUserAndRoleRelationByUID(Long userId) {
        String[] keys = { "userId" };
        Object[] values = { userId };
        return criteriaExecuteList(SysUsersRoles.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param recId
     * @return
     * @see com.nuls.io.dao.UserInfoDao#getUserDefinedByRecId(java.lang.Long)
     */
    @Override
    public UserDefined getUserDefinedByRecId(Long recId) {
        String[] keys = { "recId" };
        Object[] values = { recId };
        return criteriaExecuteUniqueResult(UserDefined.class, keys, values);
    }

    /** 
     * @param getuId
     * @see com.nuls.io.dao.UserInfoDao#deleteUserDefined(java.lang.Long)
     */
    @Override
    public Boolean deleteUserDefined(Long uId) {
        String sql = "DELETE FROM user_defined  WHERE u_id=:u_id";
        String[] keys = { "u_id" };
        Object[] values = { uId };
        return sqlExecuteUpdate(sql, keys, values);
    }

    /** 
     * @param uId
     * @return
     * @see com.nuls.io.dao.UserInfoDao#getUserDefinedListByuId(java.lang.Long)
     */
    @Override
    public List<UserDefined> getUserDefinedListByuId(Long uId) {
        String[] keys = { "uId" };
        Object[] values = { uId };
        return criteriaExecuteList(UserDefined.class, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values, null, null);
    }

    /** 
     * @param uId
     * @param propertyId
     * @return
     * @see com.nuls.io.dao.UserInfoDao#getUserDefinedListByuIdAndPropertyId(java.lang.Long, java.lang.Long)
     */
    @Override
    public UserDefined getUserDefinedListByuIdAndPropertyId(Long uId, Long propertyId) {
        String[] keys = { "uId", "propertyId" };
        Object[] values = { uId, propertyId };
        return criteriaExecuteUniqueResult(UserDefined.class, keys, values);
    }

    /** 
     * @return
     * @see com.nuls.io.dao.UserInfoDao#getAllUsers()
     */
    @Override
    public List<Object[]> getAllUsers() {
        String sql = "SELECT ui.u_id, ui.title, li.login_name, ui.description, ( SELECT GROUP_CONCAT(sr.role_name) FROM sys_roles sr, sys_users_roles sur WHERE sur.user_id = ui.u_id AND sur.role_id = sr.role_id ) AS role_name, ui.gmt_create FROM user_info ui, logon_info li WHERE ui.u_id = li.u_id AND ui.disable_flag = 1 AND li.disable_flag = 1 AND ui.user_type != 1";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

    /** 
     * @return
     * @see com.nuls.io.dao.UserInfoDao#getUserInfoList()
     */
    @Override
    public List<Object[]> getUserInfoList() {
        String sql = "SELECT ui.u_id, ui.title, ui.description, sr.role_name, ( SELECT g.group_id FROM user_group g WHERE g.u_id = ui.u_id ) AS groupId FROM user_info ui, sys_roles sr, sys_users_roles sur WHERE ui.u_id = sur.user_id AND sur.role_id = sr.role_id AND sr.use_flag = 1 AND ui.disable_flag = 1 AND ui.user_type != 1";
        String[] keys = {};
        Object[] values = {};
        return sqlExecuteList(null, sql, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, keys, values);
    }

}
