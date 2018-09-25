package com.nuls.io.dao;

import java.io.Serializable;
import java.util.List;

import com.nuls.io.common.MyBatisMapper;

/**
 * mybatis基础查询类，封装了对象的基本查询方法,
 * 以下方法sql实现方式，都需自行写在mapper.xml里
 * @author 
 *
 */
@MyBatisMapper
public interface BaseMapper<M, ID extends Serializable> {

	int insert(M m);

    int insertSelective(M m);
    
    int updateByPrimaryKey(M m);
    
    int updateByPrimaryKeySelective(M m);
    
    int deleteByPrimaryKey(ID id);
    
    M selectByPrimaryKey(ID id);
    
    long exists(ID id);

    List<M> findAll();
}