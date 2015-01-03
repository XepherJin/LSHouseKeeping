package com.retropoktan.lshousekeeping.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LSApplication extends Application{

	/**    当前系统类本身    **/
	private static LSApplication instance;
	/**    系统首选项    **/
	private SharedPreferences sharedPreferences;
	/**    DaoMaster    **/
	
	/**    DaoSession    **/
	
	/**    Context    **/
	private static Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		sharedPreferences = getSharedPreferences("LSInfo", Context.MODE_PRIVATE);
	}
	
	public static LSApplication getInstance() {
		return instance;
	}
	
	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}
	
	public final boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		boolean result = false;
		if (mNetworkInfo != null) {
			result = mNetworkInfo.isAvailable();
		}
		return result;
	}
}
