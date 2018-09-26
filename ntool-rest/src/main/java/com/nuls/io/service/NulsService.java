package com.nuls.io.service;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

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
	Boolean dealWithNnulUsdt(JSONObject json);
	Boolean dealWithNnulEth(JSONObject json);
	Map<String ,List<Object>> dealWithData(JSONObject json);
	Boolean dealWithNulsBtc(JSONObject json);
	Boolean dealWithBtcUsdt(JSONObject json);
	Boolean dealWithEthUsdt(JSONObject json);
	Map<String, List<Object>>  transformNulsBtc(Map<String, List<Object>> mapBtcUstd,Map<String, List<Object>> mapNulsBTC);
	Map<String, List<Object>> transformNulsEth(Map<String, List<Object>> mapEtcUstd,Map<String, List<Object>> mapNulsEth);
}