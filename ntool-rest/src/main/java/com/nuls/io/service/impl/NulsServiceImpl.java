package com.nuls.io.service.impl;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nuls.io.dao.BtcUsdtDao;
import com.nuls.io.dao.EthUsdtDao;
import com.nuls.io.dao.NulsBtcDao;
import com.nuls.io.dao.NulsEthDao;
import com.nuls.io.dao.NulsUsdtDao;
import com.nuls.io.model.entity.BtcUsdt;
import com.nuls.io.model.entity.EthUsdt;
import com.nuls.io.model.entity.NulsBtc;
import com.nuls.io.model.entity.NulsEth;
import com.nuls.io.model.entity.NulsUsdt;
import com.nuls.io.service.NulsService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	//private Lock lock=new ReentrantLock();
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
	
	public Map<String ,List<Object>> dealWithData(JSONObject json,String flag){
		
		Map<String ,List<Object>> map=new ConcurrentHashMap<String ,List<Object>>();
		List<Object> bidsList=null;
		List<Object> asksList=null;
		//lock.lock();
		try {
			   Boolean success=(Boolean) json.get("success");
			   if(!success){
		     		  return null;
		       }
	     	   String lastUpdateId= json.get("lastUpdateId").toString();
	     	   JSONArray bidsAttrs=   json.getJSONArray("bids");//卖深度
	     	  if(lastUpdateId==null||bidsAttrs==null){
	     		  return null;
	     	  }
	     	  DecimalFormat df=null;
			  do{
					if("ETHUSDT".equals(flag)||"BTCUSDT".equals(flag)||"NULSUSDT".equals(flag)){
						 df = new DecimalFormat("0.00");
						break;
					}
					if("NULSBTC".equals(flag)){
						 df = new DecimalFormat("0.000000");
						break;
					}
					if("NULSETH".equals(flag)){
						 df = new DecimalFormat("0.00000");
						break;
					}
					     df = new DecimalFormat("0.00000000");
				}while(false);
			  bidsList= amountTotal(bidsAttrs, lastUpdateId,  df, "bids");
			  
	     	  JSONArray asksAttrs=  json.getJSONArray("asks");//买深度
	     	  
	     	  if(asksAttrs==null){
	     		  return null;
	     	  }
	     	 asksList= amountTotal( asksAttrs, lastUpdateId,  df, "asks");
     	  }catch(Exception e){
  			  e.printStackTrace();
  		 }finally{
  			//lock.unlock();
  		 };
		Collections.reverse(bidsList);
		List<Object> subListAsks=null; //卖
		List<Object> subListBids=null;//买
		if (asksList.size()>300) {
			subListAsks=getData(asksList,1,300);
			map.put("asks", subListAsks);
		}else{
			map.put("asks", asksList);
		}
		if (bidsList.size()>300) {
			subListBids=getData(bidsList,1,300);
			map.put("bids", subListBids);
		}else{
			map.put("bids", bidsList);
		}
		
		return map;
	}
	public List<Object> getData(List<Object> list,int from,int to){
		if(from>to||list.size()<to){
			return null;
		}
		List<Object> result=new ArrayList<Object>();
		for(int i=from;i<to;i++){
			result.add(list.get(i));
		}
		return result;
	}	
	public List<Object>  amountTotal( JSONArray asksAttrs,String lastUpdateId, DecimalFormat df,String adksOrbids) {
		
		List<Object>  result=new ArrayList<Object>();;
		try{
			DecimalFormat dfAmount = new DecimalFormat("0.00");
			Calendar calendar=	Calendar.getInstance();
		
			
			//去重后的list
			Set<String> asksSet = new TreeSet<String>();//去重
			for(int i=0;i<asksAttrs.length();i++){
				 JSONArray jsonArray=  asksAttrs.getJSONArray(i);
	  		     String   price=  jsonArray.get(0).toString();
	  		     String  priceD= df.format(Double.parseDouble(price));//把价格统一转
				 asksSet.add(priceD);
			}
			
			//循环set  有多少个价格的数据
			//统计相同的价格的amount
		   
			asksSet.forEach((s)->{
				Double asksTotalAmount=0.0;
				Map<String, Object> mapFor=new HashMap<String, Object>();
				String priceD=null;
				for(int j=0;j<asksAttrs.length();j++){
					try {
						JSONArray jsonArray = asksAttrs.getJSONArray(j);
						String price=  jsonArray.get(0).toString();
						priceD= df.format(Double.parseDouble(price));
						Double amount=  Double.parseDouble(jsonArray.get(1).toString());
						if (priceD.equals(s)) {//如果价格相等 累加
								asksTotalAmount+=Double.valueOf(amount);
						 }
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				mapFor.put("price", df.format(Double.parseDouble(s)));
		     	mapFor.put("amount",  dfAmount.format(asksTotalAmount));
	     	    mapFor.put("lastUpdateId", lastUpdateId);
	     	    mapFor.put("gmtCreate", calendar.getTime());
	     	    mapFor.put("bidsOrAsk", adksOrbids);
	     	    mapFor.put("total", df.format(Double.parseDouble(s)*asksTotalAmount));
	     	    result.add(mapFor);
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		Object obj=result;
    	List<Map<String ,Object>> askListSort=(List<Map<String, Object>>) obj;
    	askListSort.sort((t1,t2) -> (Double.valueOf( t1.get("price").toString()) ).compareTo(Double.valueOf(t2.get("price").toString())));
		return result;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Async("myExecutor")
	public Boolean dealWithNnulUsdt(JSONObject json) {
		 Map<String ,List<Object>> map=dealWithData(json,"");//""  保存完整数据
		 if(map==null){
				return false;
		 }
		 List<Object> asks= map.get("asks");
		 List<Object> bids= map.get("bids");
		 Object asksObj = (Object) asks;
		 Object bidsObj = (Object) bids;
		 List<NulsUsdt> asksList = (List<NulsUsdt>)asksObj ;
		 List<NulsUsdt> bidsList = (List<NulsUsdt>)bidsObj ;
     	 addNulsUsdt(asksList);
     	 addNulsUsdt(bidsList);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Async("myExecutor")
	public Boolean dealWithNnulEth(JSONObject json) {
		 Map<String ,List<Object>> map=dealWithData(json,"");
		 if(map==null){
				return false;
		 }
		 List<Object> asks= map.get("asks");
		 List<Object> bids= map.get("bids");
		 Object asksObj = (Object) asks;
		 Object bidsObj = (Object) bids;
		 List<NulsEth> asksList = (List<NulsEth>)asksObj ;
		 List<NulsEth> bidsList = (List<NulsEth>)bidsObj ;
		 addNulsEth(asksList);
		 addNulsEth(bidsList);
		 return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Async("myExecutor")
	public Boolean dealWithNulsBtc(JSONObject json) {
		Map<String ,List<Object>> map=dealWithData(json,"");
		if(map==null){
			return false;
		}
		List<Object> asks= map.get("asks");
		List<Object> bids= map.get("bids");
		Object asksObj = (Object) asks;
		Object bidsObj = (Object) bids;
		List<NulsBtc> asksList = (List<NulsBtc>)asksObj ;
		List<NulsBtc> bidsList = (List<NulsBtc>)bidsObj ;
		addNulsBtc(asksList);
		addNulsBtc(bidsList);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Async("myExecutor")
	public Boolean dealWithBtcUsdt(JSONObject json) {
		Map<String ,List<Object>> map=dealWithData(json,"");
		if(map==null){
			return false;
		}
		List<Object> asks= map.get("asks");
		List<Object> bids= map.get("bids");
		Object asksObj = (Object) asks;
		Object bidsObj = (Object) bids;
		List<BtcUsdt> asksList = (List<BtcUsdt>)asksObj ;
		List<BtcUsdt> bidsList = (List<BtcUsdt>)bidsObj ;
		addBtcUsdt(asksList);
		addBtcUsdt(bidsList);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Async("myExecutor")
	public Boolean dealWithEthUsdt(JSONObject json) {
		Map<String ,List<Object>> map=dealWithData(json,"");
		if(map==null){
			return false;
		}
		List<Object> asks= map.get("asks");
		List<Object> bids= map.get("bids");
		Object asksObj = (Object) asks;
		Object bidsObj = (Object) bids;
		List<EthUsdt> asksList = (List<EthUsdt>)asksObj ;
		List<EthUsdt> bidsList = (List<EthUsdt>)bidsObj ;
		addEthUsdt(asksList);
		addEthUsdt(bidsList);
		return true;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public Map<String, List<Object>>  transformNulsBtc(Map<String, List<Object>> mapBtcUstd,Map<String, List<Object>> mapNulsBTC) {
		
		//返回值
		Map<String ,List<Object>> map=new HashMap<String, List<Object>>();
		List<Object> asksList=new ArrayList<Object>();
		List<Object> bidsList=new ArrayList<Object>();
		//List<Object> mapBtcUsdtAsks= mapBtcUstd.get("asks");//买
		//List<Object> mapBtcUsdtBids= mapBtcUstd.get("bids");//卖
		List<Object> mapNulsBtcAsks= mapNulsBTC.get("asks");
		List<Object> mapNulsBtcBids= mapNulsBTC.get("bids");
		List<Object> asks= mapBtcUstd.get("asks");
		List<Object> bids= mapBtcUstd.get("bids");
		if (mapNulsBtcAsks==null||mapNulsBtcBids==null||asks==null||bids==null) {
			return null;
		}
		int indexAsks=asks.size()/2;
		int indexBids=bids.size()/2;
		//btc/ustd的价格取中间价格
		Map<String, Object> asksMap= (Map<String, Object>) asks.get(indexAsks);
		Map<String, Object> bidsMap =(Map<String, Object>) bids.get(indexBids);
		
		String btcUstdAsksPrice=asksMap.get("price").toString();
		Double btcUstdAsksPriceD= Double.parseDouble(btcUstdAsksPrice);
		//转换成ustd 买
		for(Object o: mapNulsBtcAsks){
			Map<String, Object> mapFor= (Map<String, Object>) o;
			String nulsBtcPrice= mapFor.get("price").toString();
		    Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
		    Double result= nulsBtcD*btcUstdAsksPriceD;
		    DecimalFormat df = new DecimalFormat("0.00");//保留几位小数，在#后添几个0即可  
		    mapFor.put("price", df.format(result));
		    asksList.add(mapFor);
		}
		
		String btcUstdBidsPrice= bidsMap.get("price").toString();
		Double btcUstdBidsPriceD= Double.parseDouble(btcUstdBidsPrice);
		//转换成ustd  卖
		for(Object o: mapNulsBtcBids){
			Map<String, Object> mapFor= (Map<String, Object>) o;
			String nulsBtcPrice=mapFor.get("price").toString();
			Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
			Double result= nulsBtcD*btcUstdBidsPriceD;
			DecimalFormat df = new DecimalFormat("0.00");//保留几位小数，在#后添几个0即可  
			mapFor.put("price", df.format(result));
			bidsList.add(mapFor);
		}
		Collections.reverse(asksList);
		Collections.reverse(bidsList);
		//Collections.sort(bidsList,Collections.reverseOrder());                 //降序排列
		//Collections.sort(asksList,Collections.reverseOrder());  
		map.put("asks", asksList);
		map.put("bids", bidsList);
		return map;
	}

	@Override
	public Map<String, List<Object>> transformNulsEth(Map<String, List<Object>> mapEtcUstd,Map<String, List<Object>> mapNulsEth) {
		//返回值
				Map<String ,List<Object>> map=new HashMap<String, List<Object>>();
				List<Object> asksList=new ArrayList<Object>();
				List<Object> bidsList=new ArrayList<Object>();
				//List<Object> mapBtcUsdtAsks= mapBtcUstd.get("asks");//买
				//List<Object> mapBtcUsdtBids= mapBtcUstd.get("bids");//卖
				List<Object> mapNulsBtcAsks= mapNulsEth.get("asks");
				List<Object> mapNulsBtcBids= mapNulsEth.get("bids");
				List<Object> asks= mapEtcUstd.get("asks");
				List<Object> bids= mapEtcUstd.get("bids");
				if (mapNulsBtcAsks==null||mapNulsBtcBids==null||asks==null||bids==null) {
					return null;
				}
				int indexAsks=asks.size()/2;
				int indexBids=bids.size()/2;
				//btc/ustd的价格取中间价格
				Map<String, Object> asksMap= (Map<String, Object>) asks.get(indexAsks);
				Map<String, Object> bidsMap =(Map<String, Object>) bids.get(indexBids);
				
				String btcUstdAsksPrice=(String) asksMap.get("price");
				Double btcUstdAsksPriceD= Double.parseDouble(btcUstdAsksPrice);
				//转换成ustd 买
				for(Object o: mapNulsBtcAsks){
					Map<String, Object> mapFor= (Map<String, Object>) o;
					String nulsBtcPrice=mapFor.get("price").toString();
				    Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
				    Double result= nulsBtcD*btcUstdAsksPriceD;
				    DecimalFormat df = new DecimalFormat("0.00");//保留几位小数，在#后添几个0即可  
				    mapFor.put("price", df.format(result));
				    asksList.add(mapFor);
				}
				
				String btcUstdBidsPrice=(String) bidsMap.get("price");
				Double btcUstdBidsPriceD= Double.parseDouble(btcUstdBidsPrice);
				//转换成ustd  卖
				for(Object o: mapNulsBtcBids){
					Map<String, Object> mapFor= (Map<String, Object>) o;
					String nulsBtcPrice= mapFor.get("price").toString();
					Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
					Double result= nulsBtcD*btcUstdBidsPriceD;
					DecimalFormat df = new DecimalFormat("0.00");//保留几位小数，在#后添几个0即可  
					mapFor.put("price", df.format(result));
					bidsList.add(mapFor);
				}
				Collections.reverse(asksList);
				Collections.reverse(bidsList);
				//Collections.sort(bidsList,Collections.reverseOrder());                 //降序排列
				//Collections.sort(asksList,Collections.reverseOrder());  
				map.put("asks", asksList);
				map.put("bids", bidsList);
				return map;
	}
   
}

