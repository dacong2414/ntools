package com.nuls.io.config.scheduled;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nuls.io.common.client.BinanceApiClient;
import com.nuls.io.model.entity.NulsUsdt;
import com.nuls.io.service.NulsService;

@Slf4j
@Component
public class NulsJob {
    @Resource
    private NulsService nulsService;
    @Resource
    private BinanceApiClient binanceApiClient;
  
    /**
     * 定时任务
     */
  // @Scheduled(fixedRate = 15 * 1000)
    public void getData() {
       Calendar calendar=	Calendar.getInstance();
       long startTime = System.currentTimeMillis();
       JSONObject json=  binanceApiClient.getOrderBooks("NULSUSDT", 1000);
       try {
    	   String lastUpdateId= json.get("lastUpdateId").toString();
    	   JSONArray bidsAttrs=   json.getJSONArray("bids");//卖深度
    	   List<NulsUsdt> list=new  ArrayList<NulsUsdt>();
    	   for(int i=0;i<bidsAttrs.length();i++){
    		     NulsUsdt nulsUsdt=new NulsUsdt();
		        JSONArray jsonArray=  bidsAttrs.getJSONArray(i);
				String price=  jsonArray.get(0).toString();
				String  amount=  jsonArray.get(1).toString();
				nulsUsdt.setPrice(price);
				nulsUsdt.setAmount(amount);
				nulsUsdt.setLastUpdateId(lastUpdateId);
				nulsUsdt.setGmtCreate(calendar.getTime());
				nulsUsdt.setBidsOrAsk("bids");
				list.add(nulsUsdt);
    	  }
    	  JSONArray asksAttrs=  json.getJSONArray("asks");//买深度
    	  for(int i=0;i<asksAttrs.length();i++){
			  	NulsUsdt nulsUsdt=new NulsUsdt();
			    JSONArray jsonArray=  asksAttrs.getJSONArray(i);
				String price=  jsonArray.get(0).toString();
				String  amount=  jsonArray.get(1).toString();
				nulsUsdt.setPrice(price);
				nulsUsdt.setAmount(amount);
				nulsUsdt.setLastUpdateId(lastUpdateId);
				nulsUsdt.setGmtCreate(calendar.getTime());
				nulsUsdt.setBidsOrAsk("asks");
				list.add(nulsUsdt);
    	  }
    	  nulsService.addNulsUsdt(list);
    	 System.out.println("getData:"+ (System.currentTimeMillis() - startTime+"#####")+"lastUpdateId:"+lastUpdateId);
       } catch (JSONException e) {
    	   e.printStackTrace();
       }
      
    }

   
}