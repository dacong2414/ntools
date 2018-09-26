package com.nuls.io.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nuls.io.model.entity.BtcUsdt;
import com.nuls.io.model.entity.NulsBnb;
import com.nuls.io.model.entity.NulsBtc;



@Mapper
public interface BtcUsdtDao extends BaseMapper<List<BtcUsdt>, String> {
	int insert(List<BtcUsdt> btcUsdtList);
}