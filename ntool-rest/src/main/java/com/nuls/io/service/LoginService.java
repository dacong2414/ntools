package com.nuls.io.service;

import java.util.List;

import com.nuls.io.model.entity.LogonInfo;

/**
 * 登录服务
 * @author hhu
 * @version $Id: LoginService.java, v 0.1 2017年6月13日 上午10:26:07 hhu Exp $
 */
public interface LoginService {

    /**
     *
     * @param loginName
     * @return
     */
    LogonInfo getLogonInfo(String loginName);

    /**
     * 通过uId 获取LogonInfo信息 userinfo  usergroup sysrole
     * @param uId
     */
    String getLogonInfo(Long uId);

    /**
     *修改单个用户信息（管理员个人修改页）
     * @param uId
     * @param title
     * @param description
     * @param loginName
     * @param loginPwd
     * @return
     */
    Boolean modLogonInfo(Long uId, String title, String description, String loginName,
                         String loginPwd);

    /**
     * 获取当前登录人信息
     * @return
     */
    LogonInfo getCurrentLogonInfos();

    /**
     *获取所有用户信息-->做分组可见用
     * @return
     */
    List<Object[]> getAllUsers();

}
