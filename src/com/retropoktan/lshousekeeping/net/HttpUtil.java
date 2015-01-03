package com.retropoktan.lshousekeeping.net;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public final class HttpUtil {
	
	public static final String ContentTypeJson = "application/json";
	
	private static AsyncHttpClient client = new AsyncHttpClient();
    
	static
    {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }
    
	public static void get(String urlString, AsyncHttpResponseHandler res)    //用一个完整url获取一个string对象
    {
        client.get(urlString, res);
    }
    
	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res)   //url里面带参数
    {
        client.get(urlString, params, res);
    }
    
    public static void get(String urlString, JsonHttpResponseHandler res)   //不带参数，获取json对象或者数组
    {
        client.get(urlString, res);
    }
    
    public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res)   //带参数（键值对），获取json对象或者数组
    {
        client.get(urlString, params, res);
    }
    
    public static void get(String urlString, BinaryHttpResponseHandler bHandler)   //下载数据使用，会返回byte数据
    {
        client.get(urlString, bHandler);
    }
    
    public static void post(String urlString, AsyncHttpResponseHandler res)
    {
    	client.post(urlString, res);
    }
    
    public static void post(String urlString, RequestParams params, AsyncHttpResponseHandler res)
    {
    	client.post(urlString, params, res);
    }
    
    public static void post(String urlString, JsonHttpResponseHandler res)
    {
    	client.post(urlString, res);
    }
    
    public static void post(String urlString, RequestParams params, JsonHttpResponseHandler res)
    {
    	client.post(urlString, params, res);
    }
    
    public static void post(String urlString, BinaryHttpResponseHandler res)
    {
    	client.post(urlString, res);
    }
    /**    带json参数    **/
    public static void post(Context context, String urlString, StringEntity entity, String contentType, JsonHttpResponseHandler res) {
    	client.post(context, urlString, entity, contentType, res);
    }
    
    public static AsyncHttpClient getClient()
    {
        return client;
    }
}
