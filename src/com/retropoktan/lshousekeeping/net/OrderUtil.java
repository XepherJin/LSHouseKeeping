package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Context;
import android.widget.Toast;

public class OrderUtil {
	
	public static final String CHARSET = "UTF-8";

	private static void requestOrder(final Context context, String name, String address) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("address", address);
			StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject), CHARSET);
			HttpUtil.post(context, "", stringEntity, HttpUtil.ContentTypeJson, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
					Toast.makeText(context, "网络发生错误", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
				}
				
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
