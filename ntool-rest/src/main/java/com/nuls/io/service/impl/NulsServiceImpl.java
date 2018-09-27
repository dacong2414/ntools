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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String ,List<Object>> dealWithData(JSONObject json){
		Map<String ,List<Object>> map=new HashMap<String ,List<Object>>();
		Calendar calendar=	Calendar.getInstance();
		List<Object> bidsList=null;
		List<Object> asksList=null;
		try {
	     	   String lastUpdateId= json.get("lastUpdateId").toString();
	     	   JSONArray bidsAttrs=   json.getJSONArray("bids");//卖深度
	     	    bidsList=new  ArrayList<Object>();
	     	   for(int i=0;i<bidsAttrs.length();i++){
	     		    Map<String ,Object> mapFor=new HashMap<String, Object>();
	 		        JSONArray jsonArray=  bidsAttrs.getJSONArray(i);
	 				String price=  jsonArray.get(0).toString();
	 				String  amount=  jsonArray.get(1).toString();
					Double priceD= Double.parseDouble(price);
					Double amountD= Double.parseDouble(amount);
					Double totalD= priceD*amountD;
					
	 				DecimalFormat df = new DecimalFormat("0.00000000");
	 				mapFor.put("price", df.format(priceD));
	 				mapFor.put("amount", amount);
	 				mapFor.put("lastUpdateId", lastUpdateId);
	 				mapFor.put("gmtCreate", calendar.getTime());
	 				mapFor.put("bidsOrAsk", "bids");
	 				mapFor.put("total", df.format(totalD));
	 				bidsList.add(mapFor);
	     	  }
	     	  JSONArray asksAttrs=  json.getJSONArray("asks");//买深度
	     	  asksList=new  ArrayList<Object>();
	     	  for(int i=0;i<asksAttrs.length();i++){
	     		 Map<String ,Object> mapFor=new HashMap<String, Object>();
	 			    JSONArray jsonArray=  asksAttrs.getJSONArray(i);
	 				String price=  jsonArray.get(0).toString();
	 				String  amount=  jsonArray.get(1).toString();
	 				Double priceD= Double.parseDouble(price);
	 				Double amountD= Double.parseDouble(amount);
					Double totalD= priceD*amountD;
	 				DecimalFormat df = new DecimalFormat("0.00000000");//防止1.7399E-4
	 				mapFor.put("price", df.format(priceD));
	 				mapFor.put("amount", amount);
	 				mapFor.put("lastUpdateId", lastUpdateId);
	 				mapFor.put("gmtCreate", calendar.getTime());
	 				mapFor.put("bidsOrAsk", "asks");
	 				mapFor.put("total", df.format(totalD));
	 				asksList.add(mapFor);
	     	 } 
     	  }catch(Exception e){
  			  e.printStackTrace();
  		 }
		map.put("bids", bidsList);
		map.put("asks", asksList);
		return map;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Async("myExecutor")
	public Boolean dealWithNnulUsdt(JSONObject json) {
		 Map<String ,List<Object>> map=dealWithData(json);
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
		 Map<String ,List<Object>> map=dealWithData(json);
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
		Map<String ,List<Object>> map=dealWithData(json);
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
		Map<String ,List<Object>> map=dealWithData(json);
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
		Map<String ,List<Object>> map=dealWithData(json);
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
		
		String btcUstdAsksPrice=(String) asksMap.get("price");
		Double btcUstdAsksPriceD= Double.parseDouble(btcUstdAsksPrice);
		//转换成ustd 买
		for(Object o: mapNulsBtcAsks){
			Map<String, Object> mapFor= (Map<String, Object>) o;
			String nulsBtcPrice=(String) mapFor.get("price");
		    Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
		    Double result= nulsBtcD*btcUstdAsksPriceD;
		    DecimalFormat df = new DecimalFormat("0.00000000");//保留几位小数，在#后添几个0即可  
		    mapFor.put("price", df.format(result));
		    asksList.add(mapFor);
		}
		
		String btcUstdBidsPrice=(String) bidsMap.get("price");
		Double btcUstdBidsPriceD= Double.parseDouble(btcUstdBidsPrice);
		//转换成ustd  卖
		for(Object o: mapNulsBtcBids){
			Map<String, Object> mapFor= (Map<String, Object>) o;
			String nulsBtcPrice=(String) mapFor.get("price");
			Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
			Double result= nulsBtcD*btcUstdBidsPriceD;
			DecimalFormat df = new DecimalFormat("0.00000000");//保留几位小数，在#后添几个0即可  
			mapFor.put("price", df.format(result));
			bidsList.add(mapFor);
		}
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
					String nulsBtcPrice=(String) mapFor.get("price");
				    Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
				    Double result= nulsBtcD*btcUstdAsksPriceD;
				    DecimalFormat df = new DecimalFormat("0.00000000");//保留几位小数，在#后添几个0即可  
				    mapFor.put("price", df.format(result));
				    asksList.add(mapFor);
				}
				
				String btcUstdBidsPrice=(String) bidsMap.get("price");
				Double btcUstdBidsPriceD= Double.parseDouble(btcUstdBidsPrice);
				//转换成ustd  卖
				for(Object o: mapNulsBtcBids){
					Map<String, Object> mapFor= (Map<String, Object>) o;
					String nulsBtcPrice=(String) mapFor.get("price");
					Double   nulsBtcD= Double.parseDouble(nulsBtcPrice);
					Double result= nulsBtcD*btcUstdBidsPriceD;
					DecimalFormat df = new DecimalFormat("0.00000000");//保留几位小数，在#后添几个0即可  
					mapFor.put("price", df.format(result));
					bidsList.add(mapFor);
				}
				map.put("asks", asksList);
				map.put("bids", bidsList);
				return map;
	}
   
}