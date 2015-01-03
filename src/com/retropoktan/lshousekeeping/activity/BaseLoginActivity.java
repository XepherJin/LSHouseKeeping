package com.retropoktan.lshousekeeping.activity;

import android.os.Bundle;
import android.view.View;

public class BaseLoginActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initActionBar() {
		// TODO Auto-generated method stub
		super.initActionBar();
		/**    隐藏Actionbar    **/
		titleView.setVisibility(View.GONE);
	}

}
