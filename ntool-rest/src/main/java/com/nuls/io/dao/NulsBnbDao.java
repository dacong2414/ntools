package com.nuls.io.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.NulsBnb;



@Mapper
public interface NulsBnbDao extends BaseMapper<List<NulsBnb>, String> {
	int insert(List<NulsBnb> nulsBnbList);

}