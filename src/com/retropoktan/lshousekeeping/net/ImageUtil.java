package com.retropoktan.lshousekeeping.net;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.retropoktan.lshousekeeping.constant.UrlConst;

public class ImageUtil {

	public static void getAds(final Context context, final OnRequestSuccessListener onRequestSuccessListener) {
		
		HttpUtil.get(UrlConst.GetAdUrl, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "网络出现错误", Toast.LENGTH_SHORT).show();
				onRequestSuccessListener.onRequestFail();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.get("status").toString().equals("1")) {
						JSONArray adArray = response.getJSONArray("body");
						onRequestSuccessListener.onRequestSuccess(adArray);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		});
	}
	
	public static interface OnRequestSuccessListener{
		public void onRequestSuccess(JSONArray jsonArray);
		public void onRequestFail();
	}
}
