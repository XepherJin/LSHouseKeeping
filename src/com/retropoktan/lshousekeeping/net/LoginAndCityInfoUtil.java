package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.retropoktan.lshousekeeping.constant.UrlConst;

public class LoginAndCityInfoUtil {

	public static final String CHARSET = "UTF-8";
	
	private static void tryLogin(final Context context, String name, String password, final OnLoginAndCityInfoRequestListener onLoginAndCityInfoRequestListener) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", name);
			jsonObject.put("password", password);
			StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject), CHARSET);
			HttpUtil.post(context, "", stringEntity, CHARSET, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "网络出现错误", Toast.LENGTH_SHORT).show();
					onLoginAndCityInfoRequestListener.onLoginAndCityInfoRequestFail();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					onLoginAndCityInfoRequestListener.onLoginAndCityInfoRequestSuccess();
				}
				
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void getCurrentCity(final Context context, String lat, String lng, final OnLoginAndCityInfoRequestListener onLoginAndCityInfoRequestListener) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lat", lat);
			jsonObject.put("lng", lng);
			StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject), CHARSET);
			HttpUtil.post(context, UrlConst.GetNearestCityUrl, stringEntity, CHARSET, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "获取当前城市失败", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					onLoginAndCityInfoRequestListener.onLoginAndCityInfoRequestSuccess();
				}
				
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static interface OnLoginAndCityInfoRequestListener{
		public void onLoginAndCityInfoRequestSuccess();
		public void onLoginAndCityInfoRequestFail();
	}
}
