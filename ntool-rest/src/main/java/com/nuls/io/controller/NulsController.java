package com.nuls.io.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.nuls.io.common.EhRedisCache;
import com.nuls.io.common.client.BinanceApiClient;
import com.nuls.io.model.common.IGlobalConstant;
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
	@Resource
	private EhRedisCache        ehRedisCache;
	
    @ResponseBody
    @PostMapping("/getNulsUsdt")
    public ResultInfo<Map<String, List<Object>>> getNulsUsdt(){
        Map<String, List<Object>>  map= ehRedisCache.getValue("nulsUsdt");
        return   ResultInfo.createSuccessResult(map);
    }
    @ResponseBody
    @PostMapping("/getNulsEth")
    public ResultInfo<Map<String, List<Object>>> getNulsEth(){
    	 Map<String, List<Object>>  map= ehRedisCache.getValue("nulsEth");
        return   ResultInfo.createSuccessResult(map);
    }
    @ResponseBody
    @PostMapping("/getNulsBtc")
    public ResultInfo<Map<String, List<Object>>> getNulsBtc(){
    	Map<String, List<Object>>  map= ehRedisCache.getValue("nulsBtc");
        return   ResultInfo.createSuccessResult(map);
    }
    @ResponseBody
    @PostMapping("/getNulsTotal")
    public ResultInfo<Map<String, List<Object>>> getBtcUsdt(){
    	Map<String, List<Object>>  map= ehRedisCache.getValue("nulsTotal");
    	return   ResultInfo.createSuccessResult(map);
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