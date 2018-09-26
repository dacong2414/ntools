package com.nuls.io.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.BtcUsdt;
import com.nuls.io.model.entity.EthUsdt;
import com.nuls.io.model.entity.NulsBnb;
import com.nuls.io.model.entity.NulsBtc;



@Mapper
public interface EthUsdtDao extends BaseMapper<List<EthUsdt> , String> {
	int insert(List<EthUsdt> ethUsdtList);
}