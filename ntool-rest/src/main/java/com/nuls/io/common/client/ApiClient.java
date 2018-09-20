package com.nuls.io.common.client;

import org.codehaus.jettison.json.JSONObject;

import java.util.List;

/**
 * Created by ln on 2018/3/21.
 */
public interface ApiClient {

    /**
     * 获取apikey
     * @return String
     */
    String getApiKey();

    /**
     * 获取secretKey
     * @return String
     */
    String getSecretKey();

    /**
     * 获取服务器时间
     * @return long
     */
    long getServerTime();

    /**
     * 是否支持批量下单
     * @return boolean
     */
    boolean canBathTrade();

    /**
     * 获取市场的行情数据
     * @param symbol
     * @return
     */
    JSONObject getTiker(String symbol);

    /**
     * 获取交易深度
     * @param symbol
     * @param  size
     * @return JSONObject
     */
    JSONObject getOrderBooks(String symbol, int size);

    /**
     * 批量下单
     * @param symbol
     * @param dataInfos
     * @return String
     */
    String batchNewOrder(String symbol, List<Double[]> dataInfos);

    /**
     * 下单
     * @param symbol
     * @param type
     * @param price
     * @param amount
     * @return String
     */
    String newOrder(String symbol, String type, double price, double amount);

    /**
     * 获取我的成交记录
     * @param symbol
     * @param size
     * @return String
     */
    JSONObject getMyTrades(String symbol, int size);
}
