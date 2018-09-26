package com.nuls.io.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.nuls.io.common.client.BinanceApiClient;
import com.nuls.io.model.common.ResultInfo;
import com.nuls.io.service.NulsService;


/**
 * 
 * nuls数据
 *
 */
@Controller
@RequestMapping(value = "/nuls")
public class NulsController {
	private static final Logger logger  = LoggerFactory.getLogger(NulsController.class);
	@Autowired
	NulsService nulsService;
	@Autowired
	private BinanceApiClient binanceApiClient;
	
    @ResponseBody
    @PostMapping("/getNulsUsdt")
    public ResultInfo<Map<String, List<Object>>> getNulsUsdt(){
        JSONObject json=  binanceApiClient.getOrderBooks("NULSUSDT", 1000);
        Map<String, List<Object>>  mapNnulUsdt= nulsService.dealWithData(json);
        System.out.println("11111111111"+Thread.currentThread().getName());
        nulsService.dealWithNnulUsdt(json);
        logger.info("接收："+json.toString());
        return   ResultInfo.createSuccessResult(mapNnulUsdt);
    }
    @ResponseBody
    @PostMapping("/getNulsEth")
    public ResultInfo<Map<String, List<Object>>> getNulsEth(){
        JSONObject json=  binanceApiClient.getOrderBooks("NULSETH", 1000);
        Map<String, List<Object>>  mapNnulUsdt= nulsService.dealWithData(json);
        nulsService.dealWithNnulEth(json);
        logger.info("接收："+json.toString());
        return   ResultInfo.createSuccessResult(mapNnulUsdt);
    }
    @ResponseBody
    @PostMapping("/getNulsBtc")
    public ResultInfo<Map<String, List<Object>>> getNulsBtc(){
        JSONObject json=  binanceApiClient.getOrderBooks("NULSBTC", 1000);
        Map<String, List<Object>>  mapNnulUsdt= nulsService.dealWithData(json);
        nulsService.dealWithNulsBtc(json);
        logger.info("接收："+json.toString());
        return   ResultInfo.createSuccessResult(mapNnulUsdt);
    }
    @ResponseBody
    @PostMapping("/getNulsTotal")
    public ResultInfo<Map<String, List<Object>>> getBtcUsdt(){
    	JSONObject btcUsdtjson=  binanceApiClient.getOrderBooks("BTCUSDT", 1000);
    	JSONObject etcUstdjson=  binanceApiClient.getOrderBooks("ETHUSDT", 1000);
    	JSONObject nulsBTCJson=  binanceApiClient.getOrderBooks("NULSBTC", 1000);
    	JSONObject nulsEthJson=  binanceApiClient.getOrderBooks("NULSETH", 1000);
    	Map<String, List<Object>>  mapEtcUstd= nulsService.dealWithData(etcUstdjson);
    	Map<String, List<Object>>  mapNulsBTC= nulsService.dealWithData(nulsBTCJson);
    	Map<String, List<Object>>  mapBtcUsdt= nulsService.dealWithData(btcUsdtjson);
    	Map<String, List<Object>>  mapNulsETH= nulsService.dealWithData(nulsEthJson);
    	Map<String, List<Object>>  mapNulsBtc=nulsService.transformNulsBtc(mapBtcUsdt,mapNulsBTC);
    	Map<String, List<Object>>  mapNulsEth=nulsService.transformNulsEth(mapEtcUstd,mapNulsETH);
    	Map<String, List<Object>> result= new HashMap<String, List<Object>>();
    	result.put("NulsBtcAsks", mapNulsBtc.get("asks"));
    	result.put("NulsBtcBids", mapNulsBtc.get("bids"));
    	result.put("NulsEthasks", mapNulsEth.get("asks"));
    	result.put("NulsEthBids", mapNulsEth.get("bids"));
    	//存数据库
    	nulsService.dealWithBtcUsdt(etcUstdjson);
    	nulsService.dealWithBtcUsdt(btcUsdtjson);
    	// logger.info("接收："+etcUstdjson.toString());
    	return   ResultInfo.createSuccessResult(result);
    }
    /*@ResponseBody
    @PostMapping("/getEthUsdt")
    public ResultInfo<Map<String, List<Object>>> getEthUsdt(){
    	JSONObject json=  binanceApiClient.getOrderBooks("ETHUSDT", 1000);
    	JSONObject nulsEthJson=  binanceApiClient.getOrderBooks("NULSETH", 1000);
    	Map<String, List<Object>>  mapNnulUsdt= nulsService.dealWithData(json);
    	nulsService.dealWithEthUsdt(json);
    	 logger.info("接收："+json.toString());
    	return   ResultInfo.createSuccessResult(mapNnulUsdt);
    }*/

}