package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.constant.UrlConst;
import com.retropoktan.lshousekeeping.net.HttpUtil;
import com.retropoktan.lshousekeeping.net.ImageUtil;
import com.retropoktan.lshousekeeping.net.ImageUtil.OnRequestSuccessListener;
import com.retropoktan.lshousekeeping.net.JSONUtil;
import com.retropoktan.lshousekeeping.view.ImageCycleView;
import com.retropoktan.lshousekeeping.view.ImageCycleView.ImageCycleViewListener;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;


public class MainActivity extends BaseActivity{

	private LinearLayout orderByPhoneLayout;
	private LinearLayout orderByWebLayout;
	private LinearLayout orderByCheckCostLayout;
	
	private ImageCycleView imageCycleView;
	
	private ArrayList<String> mImageUrl = null;
	
	private long mExistTime; //the time when click back key
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRightButtonShown();
        setBackButtonGone();
        getCurrentPhone();
        initViewPager();
        initButtons();
        // 开启logcat输出，方便debug，发布时请关闭
        // XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);	

        // 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);
    }

    private void initButtons() {
    	orderByPhoneLayout = (LinearLayout)findViewById(R.id.order_by_phone_layout);
    	orderByWebLayout = (LinearLayout)findViewById(R.id.order_by_web_layout);
    	orderByCheckCostLayout = (LinearLayout)findViewById(R.id.order_by_check_cost_layout);
    	orderByPhoneLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	orderByWebLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	orderByCheckCostLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	rightBtn.setBackgroundResource(R.drawable.setting_icon);
    	rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(intent);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
			}
		});
    }
    
    private void initViewPager() {
    	imageCycleView = (ImageCycleView)findViewById(R.id.ad_viewpager_main_page);
    	setAds();
	}
    
    private void setAds() {
    	ImageUtil.getAds(getApplicationContext(), new OnRequestSuccessListener() {
			
			@Override
			public void onRequestSuccess(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				mImageUrl = JSONUtil.parseAdImageViews(jsonArray);
				imageCycleView.setImageResources(mImageUrl, mAdCycleViewListener);
			}

			@Override
			public void onRequestFail() {
				// TODO Auto-generated method stub
				
			}
			
		});
    }
    
    private void getCurrentPhone() {
    	RequestParams requestParams = new RequestParams();
    	requestParams.put("area_id", "1");
    	HttpUtil.get(UrlConst.GetAreaTelUrl, requestParams, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "获取信息失败", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if (response.get("status").toString().equals("1")) {
						JSONObject jsonObject = (JSONObject)response.get("body");
						LSApplication.getInstance().setCurrentPhoneNum(jsonObject.getString("tel"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
    	});
    }

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			// TODO 单击图片处理事件
			
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);// 此处使用了ImageLoader对图片进行加装！
		}
	};
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}


	class MainPageButtonOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.order_by_phone_layout:
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("提示")
				.setMessage("您要拨号给客服吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + LSApplication.getInstance().getCurrentPhoneNum()));
						startActivity(phoneIntent);
						if (version >= 5) {
							overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
						}
					}
				})
				.setNegativeButton("取消", null).show();
				break;
			case R.id.order_by_web_layout:
				Intent WebIntent = new Intent(MainActivity.this, OrderByWebActivity.class);
				startActivity(WebIntent);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
				break;
			case R.id.order_by_check_cost_layout:
				Intent CheckCostIntent = new Intent(MainActivity.this, OrderByCheckCostActivity.class);
				startActivity(CheckCostIntent);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
				break;
			default:
				break;
			}
		}
    	
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - mExistTime > 2000) {
				Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
				mExistTime = System.currentTimeMillis();
			}
			else {
				MainActivity.this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageCycleView.pushImageCycle();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		imageCycleView.startImageCycle();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		imageCycleView.pushImageCycle();
	}
}
