package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Context;

public class LoginUtil {

	public static final String CHARSET = "UTF-8";
	
	private static void tryLogin(Context context, String name, String password) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("password", password);
			StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject));
			HttpUtil.post(context, "", stringEntity, CHARSET, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
				}
				
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
