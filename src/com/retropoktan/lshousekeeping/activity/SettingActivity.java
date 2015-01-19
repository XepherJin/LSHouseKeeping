package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.net.ImageUtil;
import com.retropoktan.lshousekeeping.net.JSONUtil;
import com.retropoktan.lshousekeeping.net.ImageUtil.OnRequestSuccessListener;
import com.retropoktan.lshousekeeping.view.ImageCycleView;
import com.retropoktan.lshousekeeping.view.ImageCycleView.ImageCycleViewListener;

public class SettingActivity extends BaseActivity{

	private Button shareButton;
	private Button clearButton;
	private Button aboutButton;
	private ImageCycleView imageCycleView;
	
	private ArrayList<String> mImageUrl = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.action_settings);
		setContentView(R.layout.setting_activity);
		initButtons();
		initViewPager();
	}

	private void initButtons() {
		shareButton = (Button)findViewById(R.id.share_textview);
		clearButton = (Button)findViewById(R.id.clear_cache_textview);
		aboutButton = (Button)findViewById(R.id.about_us_textview);
		shareButton.setOnClickListener(new SettingButtonOnLickListener());
		clearButton.setOnClickListener(new SettingButtonOnLickListener());
		aboutButton.setOnClickListener(new SettingButtonOnLickListener());
	}
	
	private void initViewPager() {
    	imageCycleView = (ImageCycleView)findViewById(R.id.ad_viewpager_setting_page);
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
    
	class SettingButtonOnLickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.share_textview:
				showShare();
				break;
			case R.id.clear_cache_textview:
				ImageLoader.getInstance().clearDiskCache();
				ImageLoader.getInstance().clearMemoryCache();
				dBHelper.deleteAllSuperItems();
				Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
				break;
			case R.id.about_us_textview:
				Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
				startActivity(intent);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
				break;
			default:
				break;
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
