package com.nuls.io.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuls.io.dao.BtcUsdtDao;
import com.nuls.io.dao.EthUsdtDao;
import com.nuls.io.dao.NulsBnbDao;
import com.nuls.io.dao.NulsBtcDao;
import com.nuls.io.dao.NulsEthDao;
import com.nuls.io.dao.NulsUsdtDao;
import com.nuls.io.model.entity.BtcUsdt;
import com.nuls.io.model.entity.EthUsdt;
import com.nuls.io.model.entity.NulsBtc;
import com.nuls.io.model.entity.NulsEth;
import com.nuls.io.model.entity.NulsUsdt;
import com.nuls.io.service.NulsService;

import java.util.List;

/**
 * 
 * @author USER
 *
 */
@Service(value = "nulsService")
public class NulsServiceImpl implements NulsService {

	@Autowired
	NulsBtcDao  nulsBtcDao;
	@Autowired
	NulsEthDao  nulsEthDao;
	@Autowired
	NulsUsdtDao nulsUsdtDao;
	@Autowired
	EthUsdtDao  ethUsdtDao;
	@Autowired
	BtcUsdtDao  btcUsdtDao;
	@Override
	public int addBtcUsdt(List<BtcUsdt> btcUsdtList) {
		return btcUsdtDao.insert(btcUsdtList);
	}

	@Override
	public int addEthUsdt(List<EthUsdt> ethUsdtList) {
		return ethUsdtDao.insert(ethUsdtList);
	}

	@Override
	public int addNulsBtc(List<NulsBtc> nulsBtcList) {
		return nulsBtcDao.insert(nulsBtcList);
	}

	@Override
	public int addNulsEth(List<NulsEth> nulsEthList) {
		return nulsEthDao.insert(nulsEthList);
	}

	@Override
	public int addNulsUsdt(List<NulsUsdt> nulsUsdtList) {
		return nulsUsdtDao.insert(nulsUsdtList);
	}

   
}