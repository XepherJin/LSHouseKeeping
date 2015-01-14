package com.retropoktan.lshousekeeping.activity;

import com.retropoktan.lshousekeeping.R;
import android.os.Bundle;

public class CheckSMSActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("号码验证");
		setContentView(R.layout.check_sms_activity);
		CheckSMSActivity.this.setResult(RESULT_OK);
	}
	
	

}
