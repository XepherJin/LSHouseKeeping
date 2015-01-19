package com.retropoktan.lshousekeeping.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.constant.UrlConst;
import com.retropoktan.lshousekeeping.net.HttpUtil;
import com.retropoktan.lshousekeeping.view.ProgressHUD;

public class CheckSMSActivity extends BaseActivity{

	private Button sendVerifyButton, resendButton;
	private EditText verifyEditText;
	private ProgressHUD progressHUD;
	private String phoneNum;
	private TimeCount timeCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("号码验证");
		Intent intent = getIntent();
		phoneNum = intent.getStringExtra("phone");
		setContentView(R.layout.check_sms_activity);
		getSMS();
		initViews();
		CheckSMSActivity.this.setResult(RESULT_OK);
	}
	
	private void initViews() {
		sendVerifyButton = (Button)findViewById(R.id.send_sms_verify_button);
		resendButton = (Button)findViewById(R.id.resend_button);
		verifyEditText = (EditText)findViewById(R.id.edittext_for_sms);
		verifyEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		sendVerifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (verifyEditText.getText().toString().trim().equals("")) {
					Toast.makeText(CheckSMSActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
				}
				else {
					checkSMS();
				}
			}
		});
		resendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSMS();
			}
		});
	}
	
	private void getSMS() {
		progressHUD = ProgressHUD.show(CheckSMSActivity.this, "载入中", true);
		try {
			RequestParams requestParams = new RequestParams();
			requestParams.put("consumer", phoneNum);
			HttpUtil.post(UrlConst.GetVerifyUrl, requestParams, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					// TODO Auto-generated method stub
					progressHUD.dismiss();
					Toast.makeText(CheckSMSActivity.this, "网络发生错误", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					progressHUD.dismiss();
					timeCount = new TimeCount(60000, 1000);
					timeCount.start();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	private void checkSMS() {
		
		progressHUD = ProgressHUD.show(CheckSMSActivity.this, "载入中", true);
		try {
			RequestParams requestParams = new RequestParams();
			requestParams.put("consumer", phoneNum);
			requestParams.put("vercode", verifyEditText.getText().toString().trim());
			HttpUtil.post(UrlConst.PhoneVerifyUrl, requestParams, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					progressHUD.dismiss();
					Toast.makeText(CheckSMSActivity.this, "网络发生错误", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					progressHUD.dismiss();
					try {
						if (response.get("status").toString().equals("1")) {
							JSONArray jsonArray = response.getJSONArray("body");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject tokenJsonObject = jsonArray.getJSONObject(i);
								LSApplication.getInstance().setToken(tokenJsonObject.get("token").toString());
							}
							CheckSMSActivity.this.setResult(RESULT_OK);
							CheckSMSActivity.this.finish();
							if (version >= 5) {
								overridePendingTransition(0, R.anim.out_from_right);
							}
						}
						else {
							Toast.makeText(CheckSMSActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
							LSApplication.getInstance().setToken("MTQyMTUwNTg1N0w0Y09kVDE1MDA4NDI3NzE1");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setResult(RESULT_CANCELED);
		super.onClick(v);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			CheckSMSActivity.this.setResult(RESULT_CANCELED);
			this.finish();
			if (version >= 5) {
				overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class TimeCount extends CountDownTimer{

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			resendButton.setClickable(true);
			resendButton.setText("重新获取");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			resendButton.setClickable(false);
			resendButton.setText(millisUntilFinished / 1000 + "秒");
		}
	}
	
}
