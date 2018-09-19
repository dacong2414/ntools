package com.nuls.io.dao;

import java.util.List;

import com.nuls.io.model.entity.SysUsersRoles;
import com.nuls.io.model.entity.UserDefined;
import com.nuls.io.model.entity.UserInfo;

public interface UserInfoDao {
    /**
     * 通过uId来获取UserInfo相关信息
     * @param uId 用户的ID
     * @return UserInfo
     */
    UserInfo getUserInfoByUID(Long uId);

    /**
     *  根据用户ID获取用户角色关系集合信息
     * @param uId 用户ID
     * @return List 集合
     */
    List<SysUsersRoles> getUserAndRoleRelationByUID(Long uId);

    /**
     * 主键id获取用户自定义属性
     * @param recId
     * @return
     */
    UserDefined getUserDefinedByRecId(Long recId);

    /**
     * 删除用户自定义的所有属性
     * @param getuId
     */
    Boolean deleteUserDefined(Long getuId);

    /**
     * 获取用户所有自定义的所有属性
     * @param uId
     * @return
     */
    List<UserDefined> getUserDefinedListByuId(Long uId);

    /**
     * 获取用户指定自定义的所有属性
     * @param uId
     * @param propertyId
     * @return
     */
    UserDefined getUserDefinedListByuIdAndPropertyId(Long uId, Long propertyId);

    /**
     * 获取所有用户信息-->做分组可见用
     * @return
     */
    List<Object[]> getAllUsers();

    /**
     * 获取所有用户 （除去管理员）
     */
    List<Object[]> getUserInfoList();
}
