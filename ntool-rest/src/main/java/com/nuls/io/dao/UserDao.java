package com.nuls.io.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.User;


@Mapper
public interface UserDao extends BaseMapper<User, String> {
    
   //int insert( @Param("userName")String userName, @Param("password") String password,  @Param("phone") String phone);
    int insert(User user);

}