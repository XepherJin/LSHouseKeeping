package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.retropoktan.lshousekeeping.constant.UrlConst;

public class OrderUtil {
	
	public static final String CHARSET = "UTF-8";

	public static void requestOrder(final Context context, String content, String name, String address, 
			int areaId, String phone) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("content", content);
			jsonObject.put("area_id", areaId);
			jsonObject.put("consumer", phone);
			jsonObject.put("name", name);
			jsonObject.put("address", address);
			StringEntity stringEntity = new StringEntity(String.valueOf(jsonObject), CHARSET);
			HttpUtil.post(context, UrlConst.MakeOrderUrl, stringEntity, HttpUtil.ContentTypeJson, new JsonHttpResponseHandler(){

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
	
	public static void getSuperItems(final Context context, final OnRequestCompleteListener onRequestCompleteListener) {
		
		HttpUtil.get(UrlConst.GetCategoryUrl, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				onRequestCompleteListener.onRequestFail();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.get("status").toString().equals("1")) {
						JSONArray jsonArray = response.getJSONArray("body");
						onRequestCompleteListener.onRequestSuccess(jsonArray);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		});
	}
	
	public static void getDetailRepairItems(String id,Context context, final OnRequestCompleteListener onRequestCompleteListener) {
		HttpUtil.get(UrlConst.GetDetailItemUrl + id, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				onRequestCompleteListener.onRequestFail();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				Log.d("asdasdasdasd", response.toString());
				try {
					if (response.get("status").toString().equals("1")) {
						JSONArray jsonArray = response.getJSONArray("body");
						Log.d("wawawa", jsonArray.toString());
						onRequestCompleteListener.onRequestSuccess(jsonArray);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		});
	}
	
	public static interface OnRequestCompleteListener{
		public void onRequestSuccess(JSONArray jsonArray);
		public void onRequestFail();
	}
}
