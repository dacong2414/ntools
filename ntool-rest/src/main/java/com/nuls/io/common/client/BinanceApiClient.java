package com.nuls.io.common.client;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ln on 2018/3/23.
 */
public class BinanceApiClient implements ApiClient {

    private static Logger log = Logger.getLogger(BinanceApiClient.class);

    private final String BASE_URL = Config.BINANCE_API_URL;

    // apiKey 为用户申请的apiKey
    private final String apiKey = Config.BINANCE_API_KEY;
    // secretKey为用户申请的secretKey
    private final String secretKey = Config.BINANCE_API_SECRET;

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 获取服务器时间
     * @return
     */
    public long getServerTime() {

        Map<String,String> headers = new HashMap<String,String>();

        String content = new HttpClientUtil().doGet(BASE_URL + "/api/v1/time", headers, "utf-8");

        try {
            JSONObject json = new JSONObject(content);
            return json.getLong("serverTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return System.currentTimeMillis();
    }

    @Override
    public boolean canBathTrade() {
        return false;
    }

    @Override
    public JSONObject getOrderBooks(String symbol, int size) {
        String params = "symbol=" + symbol + "&limit=" + size;
        String url = BASE_URL + "/api/v1/depth?" + params;

        Map<String,String> headers = new HashMap<String,String>();

        String content = new HttpClientUtil().doGet(url, headers, "utf-8");
System.out.println(content);
        JSONObject json = new JSONObject();
        try {
            if (content == null) {
                return json.put("success", false);
            }

            JSONObject resJson = new JSONObject(content);
            json.put("success", true);

            JSONArray asksJson = new JSONArray();
            JSONArray bidsJson = new JSONArray();

            JSONArray resAsksJson = resJson.getJSONArray("asks");
            JSONArray resBidsJson = resJson.getJSONArray("bids");

            for (int i = 0 ; i < resAsksJson.length() ; i++) {
                JSONArray asks = resAsksJson.getJSONArray(i);
                asksJson.put(new JSONArray().put(asks.getDouble(0)).put(asks.getDouble(1)));
            }

            for (int i = 0 ; i < resBidsJson.length() ; i++) {
                JSONArray bids = resBidsJson.getJSONArray(i);
                bidsJson.put(new JSONArray().put(bids.getDouble(0)).put(bids.getDouble(1)));
            }

            json.put("asks", asksJson);
            json.put("bids", bidsJson);

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String batchNewOrder(String symbol, List<Double[]> dataInfos) {
        return null;
    }

    @Override
    public String newOrder(String symbol, String type, double price, double amount) {

        DecimalFormat df = new DecimalFormat("#.########");

        BigDecimal b = new BigDecimal(amount);

        int scale = 8;
        if ("BTCUSDT".equals(symbol)) {
            scale = 6;
        } else if("BNBUSDT".equals(symbol)) {
            scale = 2;
        } else if("ETHUSDT".equals(symbol)) {
            scale = 5;
        } else if("NULSBTC".equals(symbol)) {
            scale = 0;
        } else if("NULSETH".equals(symbol)) {
            scale = 0;
        } else if("NULSBNB".equals(symbol)) {
            scale = 0;
        }
        amount = b.setScale(scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();

        String params = "symbol="+ symbol +"&side=" + type + "&type=LIMIT&timeInForce=GTC&quantity=" + amount + "&price=" + df.format(price) + "&recvWindow=500000&timestamp=" + getServerTime();

        params = params + "&signature=" + SHA256HMAC.HMACSHA256(params.getBytes(), secretKey.getBytes());
        String url = "https://www.binance.com/api/v3/order?" + params;

        Map<String,String> ps = new HashMap<String,String>();
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("X-MBX-APIKEY", apiKey);

        String content = new HttpClientUtil().doPost(url, ps, headers, "utf-8");
        return content;
    }

    /**
     * 获取我的成交记录
     * @param symbol
     * @return
     */
    public JSONObject getMyTrades(String symbol, int size) {
        String params = "symbol="+ symbol +"&limit=" + size + "&recvWindow=5000&timestamp=" + getServerTime();

        params = params + "&signature=" + SHA256HMAC.HMACSHA256(params.getBytes(), secretKey.getBytes());
        String url = BASE_URL + "/api/v3/myTrades?" + params;

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("X-MBX-APIKEY", apiKey);

        String content = new HttpClientUtil().doGet(url, headers, "utf-8");

        JSONObject result = new JSONObject();
        try {
            JSONArray resultArray = new JSONArray(content);

            JSONArray newArray = new JSONArray();

            for (int i = resultArray.length() - 1 ; i >= 0 ; i--) {
                JSONObject resOrder = resultArray.getJSONObject(i);

                JSONObject order = new JSONObject();

                order.put("id", resOrder.getLong("id"));
                order.put("orderId", resOrder.getLong("orderId"));
                order.put("price", resOrder.getDouble("price"));
                order.put("amount", resOrder.getDouble("qty"));
                order.put("symbol", symbol);
//                order.put("time", resOrder.getLong("time"));//"2017-01-31T00:00:00+08:00"
                order.put("type", resOrder.getBoolean("isBuyer") ? "buy" : "sell");

                newArray.put(order);
            }

            result.put("orders", newArray);
            result.put("success", true);

        } catch (Exception e) {
            log.error(e.getMessage());
            try {
                result.put("success", false);
                result.put("message", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取我的委托订单
     * @param symbol
     * @return
     */
    public String getOrders(String symbol) {
        String params = "symbol="+ symbol +"&recvWindow=5000&timestamp=" + getServerTime();

        params = params + "&signature=" + SHA256HMAC.HMACSHA256(params.getBytes(), secretKey.getBytes());
        String url = BASE_URL + "/api/v3/openOrders?" + params;

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("X-MBX-APIKEY", apiKey);

        String content = new HttpClientUtil().doGet(url, headers, "utf-8");
        return content;
    }

    /**
     * 获取市场的行情数据
     * @param symbol
     * @return JSONObject
     */
    public JSONObject getTiker(String symbol) {
        Map<String,String> headers = new HashMap<String,String>();

        String content = new HttpClientUtil().doGet(BASE_URL + "/api/v1/ticker/24hr?symbol=" + symbol, headers, "utf-8");

        JSONObject result = new JSONObject();
        try {
            JSONObject json = new JSONObject(content);

            result.put("success", true);
            result.put("time", json.getLong("closeTime"));

            result.put("buy", json.getDouble("bidPrice"));
            result.put("sell", json.getDouble("askPrice"));
            result.put("last", json.getDouble("lastPrice"));
            result.put("low", json.getDouble("lowPrice"));
            result.put("high", json.getDouble("highPrice"));

        } catch (Exception e) {
            try {
                result.put("success", false).put("message", e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {

        Config.load();

        BinanceApiClient client = new BinanceApiClient();

//        JSONObject res = client.getMyTrades("NULSUSDT", 20);

        JSONObject res = client.getTiker("NULSBTC");

//        JSONObject res = client.getAccount();

//        JSONObject res = client.getOrderBooks("NULSBTC", 1000);

//        String res = client.newOrder("NULSUSDT", "buy", 2.1, 26.13);

        System.out.println(res);
    }
}
