package com.retropoktan.lshousekeeping.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.retropoktan.lshousekeeping.application.LSApplication;

import android.content.Context;

public class DBHelper {

	private static DBHelper instance;
	private static Context appContext;
	private DaoSession mDaoSession;
	private SuperItemDao superItemDao;
	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper();
			if (appContext == null) {
				appContext = context.getApplicationContext();
			}
			instance.mDaoSession = LSApplication.getDaoSession(context);
			instance.superItemDao = instance.mDaoSession.getSuperItemDao();
		}
		return instance;
	}
	
	public List<SuperItem> loadAllSuperItems() {
		return superItemDao.loadAll();
	}
	
	public List<SuperItem> querySuperItems(String where, String... params) {
		return superItemDao.queryRaw(where, params);
	}
	
	public long saveSuperItem(SuperItem superItem) {
		return superItemDao.insertOrReplace(superItem);
	}
	
	public void saveSuperItems(final List<SuperItem> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		superItemDao.getSession().runInTx(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < list.size(); i++) {
					SuperItem superItem = list.get(i);
					superItemDao.insertOrReplace(superItem);
				}
			}
		});
	}
	
	public void deleteAllSuperItems() {
		superItemDao.deleteAll();
	}
	
	public void deleteSuperItem(SuperItem superItem) {
		superItemDao.delete(superItem);
	}
	
	public void saveSuperItemsFromJSONArray(JSONArray jsonArray, List<SuperItem> superItemsList) {
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				SuperItem superItem = new SuperItem(String.valueOf(jsonObject.getInt("category_id")), jsonObject.get("category").toString(), "http://imgs.xiuna.com/xiezhen/2013-3-20/1/12.jpg");
				saveSuperItem(superItem);
				superItemsList.add(superItem);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
