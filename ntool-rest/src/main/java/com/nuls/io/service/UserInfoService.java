package com.nuls.io.service;

import java.util.List;

import com.nuls.io.model.entity.UserDefined;

/**
 * 用户服务
 *
 * @author ambition
 * @version $Id: UserInfoService.java, v 0.1 2017年8月17日 上午10:05:05 ambition Exp $
 */
public interface UserInfoService {
    /**
     * 新增或者修改用户
     *
     * @param title
     * @param description
     * @param loginName
     * @param loginPwd
     * @param roleId
     * @param groupId 下级组id
     * @param fieldId
     * @param userType
     * @return
     */
    public Boolean addOrModUserInfo(Long uId, String title, String description, String loginName,
                                    String loginPwd, Long[] roleId, Long groupId, Long userType);

    /**
     * 删除用户
     * @param uId
     */
    public Boolean delUserInfo(Long uId);

    /**
     * 验证登录名是否存在
     * @param loginName
     * @param uId
     * @param fieldId
     * @return
     */
    public Boolean existBoolean(String loginName, Long uId);

    /**
     * 新增或修改用户自定义属性
     * @param userDefined
     */
    public Boolean addOrModUserDefined(UserDefined userDefined);

    /**
     *获取用户所有自定义属性信息
     * @param uId
     * @return
     */
    public List<UserDefined> getUserDefinedListByuId(Long uId);

    /**
     *  删除用户自定义属性
     * @param recId
     */
    public Boolean deleteUserDefined(Long recId);

    /**
     * 获取用户指定自定义属性
     * @param uId
     * @param propertyId
     * @return
     */
    public UserDefined getUserDefinedListByuIdAndPropertyId(Long uId, Long propertyId);

}
