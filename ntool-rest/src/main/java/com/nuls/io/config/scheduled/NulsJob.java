package com.nuls.io.config.scheduled;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nuls.io.common.EhRedisCache;
import com.nuls.io.common.client.BinanceApiClient;
import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.service.NulsService;

@Slf4j
@Component
public class NulsJob {
    @Resource
    private NulsService nulsService;
    @Resource
    private EhRedisCache        ehRedisCache;
    
  
    /**
     * 定时任务
     */
	@Scheduled(fixedRate = 15 * 1000)
    public void getNulsUsdt() {
    	try{
    	 BinanceApiClient client = new BinanceApiClient();
    	 JSONObject json=  client.getOrderBooks("NULSUSDT", IGlobalConstant.number);
    	 System.out.println("getNulsUsdt###json:"+ json);
         Map<String ,List<Object>> map=nulsService.dealWithData(json,"NULSUSDT");
		 if(map==null){
				return ;
		 }
		 //ehRedisCache.evict("nulsUsdt");
		 ehRedisCache.put("nulsUsdt", map);
		 nulsService.dealWithNnulUsdt(json);
		// nulsService.addNulsUsdt(asksList);
		 //nulsService.addNulsUsdt(bidsList);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
    }
    
    /**
     * 定时任务
     */
	@Scheduled(fixedRate = 15 * 1000)
    public void getNulsEth() {
    	try{
    	 BinanceApiClient client = new BinanceApiClient();
    	 JSONObject json=  client.getOrderBooks("NULSETH", IGlobalConstant.number);
    	 System.out.println("getNulsEth###json:"+ json);
         Map<String ,List<Object>> map=nulsService.dealWithData(json,"NULSETH");
		 if(map==null){
				return ;
		 }
		// ehRedisCache.evict("nulsEth");
		 ehRedisCache.put("nulsEth", map);
		 //nulsService.addNulsEth(asksList);
		 //nulsService.addNulsEth(bidsList);
		 nulsService.dealWithNnulEth(json);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
    }

    /**
     * 定时任务
     */
	@Scheduled(fixedRate = 15 * 1000)
    public void getNulsBtc() {
    	try{
    	 BinanceApiClient client = new BinanceApiClient();
    	 JSONObject json=  client.getOrderBooks("NULSBTC", IGlobalConstant.number);
    	 System.out.println("getNulsBtc###json:"+ json);
         Map<String ,List<Object>> map=nulsService.dealWithData(json,"NULSBTC");
		 if(map==null){
				return ;
		 }
		// ehRedisCache.evict("nulsBtc");
		 ehRedisCache.put("nulsBtc", map);
		 //nulsService.addNulsBtc(asksList);
		 //nulsService.addNulsBtc(bidsList);
		 nulsService.dealWithNulsBtc(json);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
    }
    
    /**
     * 定时任务
     */
    @SuppressWarnings("unchecked")
	@Scheduled(fixedRate = 15 * 1000)
    public void getTotal() {
    	try{
    		Calendar calendar= Calendar.getInstance();
	    	BinanceApiClient client = new BinanceApiClient();
	    	JSONObject btcUsdtjson=  client.getOrderBooks("BTCUSDT", IGlobalConstant.number);
	     	JSONObject etcUstdjson=  client.getOrderBooks("ETHUSDT", IGlobalConstant.number);
	     	JSONObject nulsBTCJson=  client.getOrderBooks("NULSBTC", IGlobalConstant.number);
	     	JSONObject nulsEthJson=  client.getOrderBooks("NULSETH", IGlobalConstant.number);
	     	JSONObject nulsUsdtJson=  client.getOrderBooks("NULSUSDT", IGlobalConstant.number);
	     	Map<String ,List<Object>>  mapEtcUstd= nulsService.dealWithData(etcUstdjson,"ETHUSDT");
	     	Map<String ,List<Object>>  mapNulsBTC= nulsService.dealWithData(nulsBTCJson,"NULSBTC");
	     	Map<String ,List<Object>>  mapBtcUsdt= nulsService.dealWithData(btcUsdtjson,"BTCUSDT");
	     	Map<String ,List<Object>>  mapNulsETH= nulsService.dealWithData(nulsEthJson,"NULSETH");
	     	Map<String ,List<Object>>  mapNulsUsdt= nulsService.dealWithData(nulsUsdtJson,"NULSUSDT");
	    	Map<String, List<Object>>  mapNulsBtc=nulsService.transformNulsBtc(mapBtcUsdt,mapNulsBTC);
	    	Map<String, List<Object>>  mapNulsEth=nulsService.transformNulsEth(mapEtcUstd,mapNulsETH);
	    	if(mapEtcUstd==null||mapNulsBTC==null||mapBtcUsdt==null||mapNulsETH==null||mapNulsBtc==null||mapNulsEth==null){
	    		return ;
	    	}
	    	List<Object>  asksList=new ArrayList<Object>();
	    	List<Object>  bidsList=new ArrayList<Object>();
	    	asksList.addAll(mapNulsBtc.get("asks"));
	    	asksList.addAll(mapNulsEth.get("asks"));
	    	asksList.addAll(mapNulsUsdt.get("asks"));
	    	Object obj=asksList;
	    	List<Map<String ,Object>> askListSort=(List<Map<String, Object>>) obj;
	    	
	    	
	    	
	    	bidsList.addAll(mapNulsBtc.get("bids"));
	    	bidsList.addAll(mapNulsEth.get("bids"));
	    	bidsList.addAll(mapNulsUsdt.get("bids"));
	    	Object obj2=bidsList;
	    	List<Map<String ,Object>> bidsListSort=(List<Map<String, Object>>) obj2;
	    	//排序
	    	//bidsListSort.sort((t1, t2) -> (Double.valueOf( t1.get("price").toString()) ).compareTo(Double.valueOf(t2.get("price").toString())));
			Map<String,  Object> result= new HashMap<String, Object>();
	    	//相同的price amount相加  asks
			DecimalFormat df = new DecimalFormat("0.00");//保留几位小数，在#后添几个0即可  
			//去重后的list
			Set<String> asksSet = new TreeSet<String>();//去重 有序
			for(int i=0;i<askListSort.size();i++){
				Map<String, Object> map=askListSort.get(i);
				String price= map.get("price").toString();
				String  priceD= df.format(Double.parseDouble(price));//把价格统一转
				asksSet.add(priceD);
			}
			//循环set  有多少个价格的数据
			//统计相同的价格的amount
			List<Map<String ,Object>>  resultAsks=new ArrayList<Map<String,Object>>();
			asksSet.forEach((s)->{
				Double asksTotalAmount=0.0;
				Map<String, Object> mapFor=new HashMap<String, Object>();
				for(int j=0;j<askListSort.size();j++){
					Map<String, Object> map=askListSort.get(j);
					String price=	map.get("price").toString();
					String  priceD= df.format(Double.parseDouble(price));
					if (priceD.equals(s)) {//如果价格相等 累加
						asksTotalAmount+=Double.valueOf(map.get("amount").toString());
					}
				}
				mapFor.put("price", df.format(Double.parseDouble(s.toString())));
		     	mapFor.put("amount", df.format(asksTotalAmount));
	     	    mapFor.put("lastUpdateId", mapFor.get("lastUpdateId"));
	     	    mapFor.put("gmtCreate", calendar.getTime());
	     	    mapFor.put("bidsOrAsk", "asks");
	     	    mapFor.put("total", Double.parseDouble(mapFor.get("price").toString())*asksTotalAmount);
	     	    resultAsks.add(mapFor);
	        });
				
			
			//bids 装入set
			Set<String> bidsSet = new TreeSet<String>();//去重  有序
			for(int i=0;i<bidsListSort.size();i++){
				Map<String, Object> map=bidsListSort.get(i);
				String price= map.get("price").toString();
				String  priceD= df.format(Double.parseDouble(price));//把价格统一转
				bidsSet.add(priceD);
			}
			
		
			
			//循环set  有多少个价格的数据
			//统计相同的价格的amount
			List<Map<String ,Object>>  resultBids=new ArrayList<Map<String,Object>>();
			bidsSet.forEach((s)->{
				Double bidsTotalAmount=0.0;
				Map<String, Object> mapFor=new HashMap<String, Object>();
				String lastUpdateId=null;
				for(int j=0;j<bidsListSort.size();j++){
					Map<String, Object> map=bidsListSort.get(j);
					String price=	map.get("price").toString();
					String  priceD= df.format(Double.parseDouble(price));
					lastUpdateId=map.get("lastUpdateId").toString();
					if (priceD.equals(s)) {//如果价格相等 累加
						bidsTotalAmount+=Double.valueOf(map.get("amount").toString());
					}
				}
				mapFor.put("price", df.format(Double.parseDouble(s)));
		     	mapFor.put("amount", df.format(bidsTotalAmount));
	     	    mapFor.put("lastUpdateId", lastUpdateId);
	     	    mapFor.put("gmtCreate", calendar.getTime());
	     	    mapFor.put("bidsOrAsk", "asks");
	     	    mapFor.put("total", Double.parseDouble(s)*bidsTotalAmount);
	     	    resultBids.add(mapFor);
	        });
			//最后统一排序
			resultAsks.sort((t1,t2) -> (Double.valueOf( t1.get("price").toString()) ).compareTo(Double.valueOf(t2.get("price").toString())));
			resultBids.sort((t2,t1) -> (Double.valueOf( t1.get("price").toString()) ).compareTo(Double.valueOf(t2.get("price").toString())));
			//Collections.reverse(resultAsks);
			List<Object> subListAsks=null; //卖
			List<Object> subListBids=null;//买
			Object objAsks=resultAsks;
			Object objBids=resultBids;
			List<Object> objectMapAsks= (List<Object>) objAsks;
			List<Object> objectMapBids= (List<Object>) objBids;
			//取50条
			if (objectMapAsks.size()>300) {
				subListAsks=nulsService.getData(objectMapAsks, 1, 300);//从1开始  0 有可能价格为0
				result.put("asks", subListAsks);
			}else{
				result.put("asks", objectMapAsks);
			}
			if (objectMapBids.size()>300) {
				subListBids=nulsService.getData(objectMapBids, 1, 300);
				result.put("bids", subListBids);
			}else{
				result.put("bids", objectMapBids);
			}
			
	    	ehRedisCache.put("nulsTotal", result);
		    	//存数据库
		    nulsService.dealWithBtcUsdt(etcUstdjson);
		    nulsService.dealWithEthUsdt(btcUsdtjson);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
    }
    
  
}