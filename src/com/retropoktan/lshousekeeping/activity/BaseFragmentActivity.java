package com.retropoktan.lshousekeeping.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.retropoktan.lshousekeeping.application.LSApplication;

public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener{

	protected LSApplication mApplication;
	
	protected FragmentManager fragmentManager;
	
	private View refreshView, netView;
	private TextView refreshTip;
	/**    判断是否刷新    **/
	private boolean isRefreshing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication = (LSApplication)getApplication();
		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState != null) {
			
		}
		else {
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
