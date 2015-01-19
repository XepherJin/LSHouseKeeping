package com.retropoktan.lshousekeeping.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.retropoktan.lshousekeeping.application.LSApplication;

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
				SuperItem superItem = new SuperItem(String.valueOf(jsonObject.getInt("category_id")), jsonObject.get("category").toString(), jsonObject.getString("url"));
				superItemsList.add(superItem);
			}
			Collections.sort(superItemsList, new Comparator<SuperItem>() {

				@Override
				public int compare(SuperItem lhs, SuperItem rhs) {
					// TODO Auto-generated method stub
					int lid = Integer.parseInt(lhs.getSuperItemId());
					int rid = Integer.parseInt(rhs.getSuperItemId());
					if (lid != rid) {
						return lid - rid;
					}
					else {
						if (!lhs.getSuperItemName().equals(rhs.getSuperItemName())) {
							return lhs.getSuperItemName().compareTo(rhs.getSuperItemName());
						}
					}
					return 0;
				}
			});
			for (SuperItem superItem : superItemsList) {
				saveSuperItem(superItem);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
