package com.retropoktan.lshousekeeping.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.retropoktan.lshousekeeping.dao.DetailRepairItem;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class JSONUtil {

	public static ArrayList<String> parseAdImageViews(JSONArray jsonArray) {
		ArrayList<String> adImageViewsUrl = new ArrayList<String>();
		if (jsonArray.length() <= 0) {
			return null;
		}
		else {
			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					adImageViewsUrl.add(jsonObject.get("photo").toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return adImageViewsUrl;
	}
	
	public static ArrayList<String> parseSuperItemsImage(JSONArray jsonArray) {
		ArrayList<String> superItems = new ArrayList<String>();
		if (jsonArray.length() <= 0) {
			return null;
		}
		else {
			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					superItems.add(jsonObject.get("url").toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return superItems;
	}
	
	public static void parseDetailRepairItemTexts(JSONArray jsonArray, List<DetailRepairItem> list) {
		if (jsonArray.length() <= 0) {
			return;
		}
		else {
			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					DetailRepairItem detailRepairItem = new DetailRepairItem(String.valueOf(jsonObject.get("item_id")), jsonObject.get("content").toString(), jsonObject.get("price").toString(), jsonObject.get("title").toString());
					list.add(detailRepairItem);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
