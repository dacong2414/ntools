package com.nuls.io.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import com.nuls.io.dao.LogonInfoDao;
import com.nuls.io.dao.SysResourcesDao;
import com.nuls.io.dao.SysRolesDao;
import com.nuls.io.dao.UserInfoDao;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.entity.AccessKeyInfo;
import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.model.entity.SysResources;
import com.nuls.io.model.entity.UserInfo;
import com.nuls.io.service.EhCacheService;
import com.nuls.io.service.SpringSecurityService;

/**
 * 权限验证
 * @author chaoyuhang
 * @version $Id: SpringSecurityServiceImpl.java, v 0.1 2015年5月7日 下午3:16:51 chaoyuhang Exp $
 */
@Service
public class SpringSecurityServiceImpl extends BaseService implements SpringSecurityService {
    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityServiceImpl.class);
    @Autowired
    LogonInfoDao                loginInfoDao;
    @Autowired
    UserInfoDao                 userInfoDao;
    @Autowired
    SysRolesDao                 sysRolesdao;
    @Autowired
    SysResourcesDao             sysResourcesDao;
    @Autowired
    EhCacheService              ehCacheService;

    @Override
    public LogonInfo getByNameWithNoAuth(String loginName) {

        return loginInfoDao.getLoginInfoByName(loginName);
    }

    @Override
    public void validateUser(LogonInfo user) {
        UserInfo userInfo = userInfoDao.getUserInfoByUID(user.getuId());
        if (userInfo == null) {
            logger.error("用户信息不存在！");
            throw new AuthenticationServiceException("用户信息不存在！");
        } else {
            if (IGlobalConstant.DISABLED.equals(userInfo.getDisableFlag())) {
                logger.error("用户失效，无法登录");
                throw new AuthenticationServiceException("用户失效，无法登录");
            }
        }
    }

    @Override
    public void initData(LogonInfo loginInfo) {
        Long uId = loginInfo.getuId();
        List<SysResources> listSysresourcesDB = sysResourcesDao.getUrlResourcesByUid(uId);
        loginInfo.setUserInfo(userInfoDao.getUserInfoByUID(uId));
        loginInfo.setListSysResources(listSysresourcesDB);
        //绑定用户
        addToSession(IGlobalConstant.CURRENT_USER, loginInfo);
        //刷新缓存  分组走的缓存
        ehCacheService.refrushCache();
    }

    @Override
    public AccessKeyInfo getAccessKeyInfoById(String accessKeyId) {
        return loginInfoDao.getAccessKeyInfoById(accessKeyId);
    }
}
