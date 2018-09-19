package com.nuls.io.dao;

import com.nuls.io.model.entity.AccessKeyInfo;
import com.nuls.io.model.entity.EquipmentInfo;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.entity.MacAddressLoginName;

/**
 * 登录dao
 *
 * @author ambition
 * @version $Id: LogonInfoDao.java, v 0.1 2017年8月22日 下午2:18:24 ambition Exp $
 */
public interface LogonInfoDao {
    /**
     * 通过uId来获取logonInfo相关信息
     * @param uId 用户的ID
     * @return logonInfo
     */
    LogonInfo getLoginInfoByUID(Long uId);

    /**
     * 更新用户密码
     * @param logonInfo
     */
    boolean updateLoginInfoPwd(LogonInfo logonInfo);

    /**
     * 根据秘钥获取密文
     * @param accessKeyId
     * @return
     */
    AccessKeyInfo getAccessKeyInfoById(String accessKeyId);

    /**
     *通过登录名来获取logonInfo相关信息
     * @param loginName
     * @return
     */
    LogonInfo getLoginInfoByName(String loginName);

    /**
     * 获取设备名称
     * @param macAddress
     * @return
     */
    EquipmentInfo getEquipmentInfoByMacAdress(String macAddress);

    /**
     * 登录名和 macAdress映射关系
     * @param loginName
     * @return
     */
    MacAddressLoginName getMacAddressLoginName(String loginName);
}