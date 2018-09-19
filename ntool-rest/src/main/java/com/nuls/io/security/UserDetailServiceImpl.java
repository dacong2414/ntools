package com.nuls.io.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nuls.io.model.entity.LogonInfo;
import com.nuls.io.service.EhCacheService;
import com.nuls.io.service.SpringSecurityService;

/**
 * 初始化当前用户权限
 * @author chaoyuhang
 * @version $Id: UserDetailServiceImpl.java, v 0.1 2015年5月20日 下午12:24:41 chaoyuhang Exp $
 */
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger   logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private SpringSecurityService springSecurityService;
    @Autowired
    private EhCacheService        ehCacheService;

    /**
     * 登录之后获取当前用户信息
     * @param loginName
     * @return
     * @throws UsernameNotFoundException
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        logger.debug(">>>loadUserByUsername()-获取用户信息<<<");
        //获取用户信息
        LogonInfo user = springSecurityService.getByNameWithNoAuth(loginName);
        if (user == null) {
            logger.debug(loginName + "用户不存在！");
            throw new UsernameNotFoundException(loginName + "用户不存在！");
        }
        //封装成spring security的user
        return new User(user.getLoginName(), user.getLoginPwd(), true, true, true, true,
            ehCacheService.getAuthorities(user));
    }
}
