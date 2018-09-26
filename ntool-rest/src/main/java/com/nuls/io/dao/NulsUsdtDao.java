package com.nuls.io.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.NulsBnb;
import com.nuls.io.model.entity.NulsBtc;
import com.nuls.io.model.entity.NulsUsdt;



@Mapper
public interface NulsUsdtDao extends BaseMapper<List<NulsUsdt>, String> {
	int insert(List<NulsUsdt> nulsUsdtList);

}