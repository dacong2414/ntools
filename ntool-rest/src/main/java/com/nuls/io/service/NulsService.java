package com.nuls.io.service;

import java.util.List;

import com.nuls.io.model.entity.BtcUsdt;
import com.nuls.io.model.entity.EthUsdt;
import com.nuls.io.model.entity.NulsBtc;
import com.nuls.io.model.entity.NulsEth;
import com.nuls.io.model.entity.NulsUsdt;

/**
 * 
 * @author USER
 *
 */
public interface NulsService {

    int addBtcUsdt(List<BtcUsdt> btcUsdtList);
    int addEthUsdt(List<EthUsdt> ethUsdtList);
    int addNulsBtc(List<NulsBtc> nulsBtcList);
    int addNulsEth(List<NulsEth> nulsEthList);
    int addNulsUsdt(List<NulsUsdt> nulsUsdtList);

}