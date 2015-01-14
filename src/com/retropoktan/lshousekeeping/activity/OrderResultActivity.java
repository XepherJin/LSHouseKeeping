package com.retropoktan.lshousekeeping.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.application.LSApplication;

public class OrderResultActivity extends BaseActivity{

	private String orderResult;
	private TextView subTextView;
	private TextView orderResultTextView;
	private ImageView orderResultImageView;
	private Button orderResultButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		orderResult = intent.getExtras().getString("result");
		setTitle(orderResult);
		setContentView(R.layout.order_result_activity);
		initViews();
	}
	
	private void initViews() {
		orderResultImageView = (ImageView)findViewById(R.id.order_result_imageview);
		orderResultTextView = (TextView)findViewById(R.id.order_result_textview);
		subTextView = (TextView)findViewById(R.id.order_result_sub_textview);
		orderResultButton = (Button)findViewById(R.id.order_result_button);
		changeViews();
		orderResultButton.setOnClickListener(new OrderResultButtonListener());
	}
	
	private void changeViews() {
		if (orderResult.equals(getResources().getString(R.string.order_success))) {
			subTextView.setVisibility(View.GONE);
			orderResultImageView.setImageDrawable(getResources().getDrawable(R.drawable.success_label));
			orderResultTextView.setText("预约成功！");
			orderResultButton.setText("分享");
		}
		else if (orderResult.equals(getResources().getString(R.string.order_failed))) {
			subTextView.setVisibility(View.VISIBLE);
			orderResultImageView.setImageDrawable(getResources().getDrawable(R.drawable.fail_label));
			orderResultTextView.setText("预约失败！");
			orderResultButton.setText("电话预约");
		}
	}

	class OrderResultButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (orderResult.equals(getResources().getString(R.string.order_success))) {
				showShare();
			}
			else if (orderResult.equals(getResources().getString(R.string.order_failed))) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + LSApplication.getInstance().getCurrentPhoneNum()));
				startActivity(intent);
			}
		}
		
	}
	
	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字
		 oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		 oks.show(this);
		 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
		if (version >= 5) {
			overridePendingTransition(0, R.anim.out_from_right);
		}
		
	}
	
}
