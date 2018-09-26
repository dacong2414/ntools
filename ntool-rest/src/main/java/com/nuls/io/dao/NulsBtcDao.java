package com.nuls.io.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.NulsBnb;
import com.nuls.io.model.entity.NulsBtc;



@Mapper
public interface NulsBtcDao extends BaseMapper<List<NulsBtc>, String> {
	int insert(List<NulsBtc> nulsBtcList);

}