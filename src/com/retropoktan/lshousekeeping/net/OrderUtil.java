package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.constant.UrlConst;

public class OrderUtil {
	
	public static final String CHARSET = "UTF-8";

	public static void requestOrder(final Context context, String content, String name, String address, 
			int areaId, String phone, String timeStamp, String remark, final OnOrderRequestCompleteListener onOrderRequestCompleteListener) {
		try {
			RequestParams jsonObject = new RequestParams();
			jsonObject.put("content", content);
			jsonObject.put("area_id", areaId);
			jsonObject.put("consumer", phone);
			jsonObject.put("name", name);
			jsonObject.put("address", address);
			jsonObject.put("time", timeStamp);
			jsonObject.put("token", LSApplication.getInstance().getToken());
			jsonObject.put("remark", remark);
			Log.d("oder_request", jsonObject.toString());
			HttpUtil.post(UrlConst.MakeOrderUrl, jsonObject, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "预约失败", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					if (statusCode == 500) {
						onOrderRequestCompleteListener.onRequestFail(String.valueOf(statusCode));
					}
				}


				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					try {
						if (response.get("status").toString().equals("1")) {
							onOrderRequestCompleteListener.onRequestSuccess((JSONObject)response.get("body"));
						}
						else {
							onOrderRequestCompleteListener.onRequestFail(response.get("status").toString());
						}
						Log.d("order_response", response.toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
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
	
	public static interface OnOrderRequestCompleteListener{
		public void onRequestSuccess(JSONObject jsonObject);
		public void onRequestFail(String statusCode);
	}
}
