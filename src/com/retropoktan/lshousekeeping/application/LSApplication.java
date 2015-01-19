package com.retropoktan.lshousekeeping.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.dao.DaoMaster;
import com.retropoktan.lshousekeeping.dao.DaoMaster.OpenHelper;
import com.retropoktan.lshousekeeping.dao.DaoSession;

public class LSApplication extends Application{

	/**    当前系统类本身    **/
	private static LSApplication instance;
	/**    系统首选项    **/
	private SharedPreferences sharedPreferences;
	/**    DaoMaster    **/
	private static DaoMaster daoMaster;
	/**    DaoSession    **/
	private static DaoSession daoSession;
	/**    Context    **/
	private static Context context;
	
	private String currentPhoneNum = null;
	private String userName = null;
	private String address = null;
	private String userPhone = null;
	private String userToken = null;
	public static final String PHONE_NUM = "phoneNumber";
	public static final String USER_NAME = "user_name";
	public static final String ADDRESS = "address";
	public static final String USER_PHONE = "user_phone";
	public static final String TOKEN = "user_token";
	public static final String PASSWORD = "password";
	public static final String USER_TOKEN = "user_token";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (instance == null) {
			instance = this;			
		}
		sharedPreferences = getSharedPreferences("LSInfo", Context.MODE_PRIVATE);
		@SuppressWarnings("deprecation")
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();
		
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(displayImageOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(configuration);
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
	
	public String getCurrentPhoneNum() {
		if (currentPhoneNum == null) {
			currentPhoneNum = getSharedPreferences().getString(PHONE_NUM, null);
		}
		return currentPhoneNum;
	}
	
	public void setToken(String token) {
		if (token != null) {
			SharedPreferences.Editor editor = getSharedPreferences().edit();
			if (editor.putString(USER_TOKEN, token).commit()) {
				this.userToken = token;
			}
		}
	}
	
	public String getToken() {
		if (userToken == null) {
			userToken = getSharedPreferences().getString(USER_TOKEN, null);
		}
		return userToken;
	}
	
	public void setCurrentPhoneNum(String phoneNum) {
		if (phoneNum != null) {
			SharedPreferences.Editor editor = getSharedPreferences().edit();
			if (editor.putString(PHONE_NUM, phoneNum).commit()) {
				this.currentPhoneNum = phoneNum;
			}
		}
	}
	
	public String getUserPhoneNum() {
		if (userPhone == null) {
			userPhone = getSharedPreferences().getString(USER_PHONE, null);
		}
		return userPhone;
	}
	
	public void setUserPhoneNum(String phoneNum) {
		if (phoneNum != null) {
			SharedPreferences.Editor editor = getSharedPreferences().edit();
			if (editor.putString(USER_PHONE, phoneNum).commit()) {
				this.userPhone = phoneNum;
			}
		}
	}
	public String getUserAddress() {
		if (address == null) {
			address = getSharedPreferences().getString(ADDRESS, null);
		}
		return address;
	}
	
	public void setUserAddress(String address) {
		if (address != null) {
			SharedPreferences.Editor editor = getSharedPreferences().edit();
			if (editor.putString(ADDRESS, address).commit()) {
				this.address = address;
			}
		}
	}
	public String getUserName() {
		if (userName == null) {
			userName = getSharedPreferences().getString(USER_NAME, null);
		}
		return userName;
	}
	
	public void setUserName(String userName) {
		if (userName != null) {
			SharedPreferences.Editor editor = getSharedPreferences().edit();
			if (editor.putString(USER_NAME, userName).commit()) {
				this.userName = userName;
			}
		}
	}
	/**
	 * 取得DaoMaster
	 * @param context
	 * @return DaoMaster实例
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			OpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "LSHouseKeepingDB", null);
			daoMaster = new DaoMaster(openHelper.getWritableDatabase());
		}
		return daoMaster;
	}
	
	/**
	 * 取得DaoSession
	 * @param context
	 * @return DaoSession实例
	 */
	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}
}
