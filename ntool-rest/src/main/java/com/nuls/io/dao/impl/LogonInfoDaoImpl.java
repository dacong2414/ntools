package com.nuls.io.dao.impl;

import java.util.Date;

import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.nuls.io.dao.LogonInfoDao;
import com.nuls.io.dao.MysqlDaoSupport;
import com.nuls.io.model.entity.AccessKeyInfo;
import com.nuls.io.model.entity.EquipmentInfo;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.entity.MacAddressLoginName;

/**
 * 
 *
 * @author ambition
 * @version $Id: LogonInfoDaoImpl.java, v 0.1 2017年8月22日 下午2:17:56 ambition Exp $
 */
@Repository
public class LogonInfoDaoImpl extends MysqlDaoSupport implements LogonInfoDao {

    @Override
    public LogonInfo getLoginInfoByUID(Long uId) {
        String[] keys = { "uId" };
        Object[] values = { uId };
        return criteriaExecuteUniqueResult(LogonInfo.class, keys, values);
    }

    @Override
    public boolean updateLoginInfoPwd(LogonInfo logonInfo) {
        String hql = "UPDATE IMARAD_LOGIN_INFO  set login_pwd=?, gmt_mod=? where login_id=?";
        Object[] values = { logonInfo.getLoginPwd(), new Date(), logonInfo.getuId() };
        Type[] types = { StringType.INSTANCE, DateType.INSTANCE, LongType.INSTANCE };
        return sqlExecuteUpdate(hql, values, types);
    }

    @Override
    public AccessKeyInfo getAccessKeyInfoById(String accessKeyId) {
        String[] keys = { "accessKeyId" };
        Object[] values = { accessKeyId };
        return criteriaExecuteUniqueResult(AccessKeyInfo.class, keys, values);
    }

    /** 
     * @param loginName
     * @return
     * @see com.nuls.io.dao.LogonInfoDao#getLoginInfoByName(java.lang.String)
     */
    @Override
    public LogonInfo getLoginInfoByName(String loginName) {
        String[] keys = { "loginName" };
        Object[] values = { loginName };
        return criteriaExecuteUniqueResult(LogonInfo.class, keys, values);
    }

    /** 
     * @param macAddress
     * @return
     * @see com.nuls.io.dao.LogonInfoDao#getEquipmentInfoByMacAdress(java.lang.String)
     */
    @Override
    public EquipmentInfo getEquipmentInfoByMacAdress(String equipmentId) {
        String[] keys = { "equipmentId" };
        Object[] values = { equipmentId };
        return criteriaExecuteUniqueResult(EquipmentInfo.class, keys, values);
    }

    /** 
     * @param loginName
     * @return
     * @see com.nuls.io.dao.LogonInfoDao#getMacAddressLoginName(java.lang.String)
     */
    @Override
    public MacAddressLoginName getMacAddressLoginName(String loginName) {
        String[] keys = { "loginName" };
        Object[] values = { loginName };
        return criteriaExecuteUniqueResult(MacAddressLoginName.class, keys, values);
    }

}
