package com.nuls.io.service;

import com.nuls.io.model.entity.AccessKeyInfo;
import com.nuls.io.model.entity.LogonInfo;

/**
 * 系统权限配置service
 *
 * @author zhangxi
 * @version $Id: SpringSecurityService.java, v 0.1 2016年5月23日 上午10:41:43 cnambition Exp $
 */
public interface SpringSecurityService {
    /**
     * 根据登录名获取系统用户登录信息（不包含权限信息）
     *
     * @param loginName
     * @return
     */
    public LogonInfo getByNameWithNoAuth(String loginName);

    /** 
     * 验证用户有效性
     *
     * @param users
     */
    public void validateUser(LogonInfo user);

    /**
     * 初始化用户权限数据
     *
     * @param logonInfo
     */
    public void initData(LogonInfo loginInfo);

    /**
     * 根据秘钥获取密文
     * @param accessKeyId
     * @return
     */
    public AccessKeyInfo getAccessKeyInfoById(String accessKeyId);
}
