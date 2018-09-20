package com.nuls.io.common.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    public String doPost(String url,Map<String,String> map, Map<String, String> headers, String charset){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();

            httpPost = new HttpPost(url);

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//连接时间

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);//数据传输时间


            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            if(headers != null) {
                iterator = headers.entrySet().iterator();
                while(iterator.hasNext()){
                    Entry<String,String> elem = (Entry<String, String>) iterator.next();
                    httpPost.setHeader(elem.getKey(),elem.getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public String doGet(String url,Map<String, String> headers, String charset){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();

            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//连接时间

            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);//数据传输时间

            httpGet = new HttpGet(url);

            if(headers != null) {
                Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
                while(iterator.hasNext()){
                    Entry<String,String> elem = (Entry<String, String>) iterator.next();
                    httpGet.setHeader(elem.getKey(),elem.getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}