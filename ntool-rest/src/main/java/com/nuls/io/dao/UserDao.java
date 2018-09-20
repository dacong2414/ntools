package com.nuls.io.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {
    
    int insert( @Param("userName")String userName, @Param("password") String password,  @Param("phone") String phone);

}