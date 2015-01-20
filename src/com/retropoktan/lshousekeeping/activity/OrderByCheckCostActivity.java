package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.adapter.SuperItemAdapter;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.dao.SuperItem;
import com.retropoktan.lshousekeeping.net.ImageUtil;
import com.retropoktan.lshousekeeping.net.ImageUtil.OnRequestSuccessListener;
import com.retropoktan.lshousekeeping.net.JSONUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil.OnRequestCompleteListener;
import com.retropoktan.lshousekeeping.view.ImageCycleView;
import com.retropoktan.lshousekeeping.view.ImageCycleView.ImageCycleViewListener;
import com.retropoktan.lshousekeeping.view.LSTopGridView;

public class OrderByCheckCostActivity extends BaseActivity{

	private ScrollView scrollView;
	private LSTopGridView topGridView;
	private LSTopGridView bottomGridView;
	private List<SuperItem> superItemsList;
	private List<SuperItem> upList;
	private List<SuperItem> downList;
	private SuperItemAdapter superItemAdapter, downItemAdapter;
	private ImageCycleView imageCycleView;
	
	private ArrayList<String> mImageUrl = null;
	
	private int requestCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.order_by_check_cost_title);
		setContentView(R.layout.web_order_select);
		setRightButtonShown();
		superItemsList = new ArrayList<SuperItem>();
		requestCode = getIntent().getIntExtra("request_code", 0);
		initViews();
		initViewPager();
		setSuperItems();
	}

	private void initViews() {
		rightBtn.setText("拨打电话");
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(OrderByCheckCostActivity.this)
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
			}
		});
		scrollView = (ScrollView)findViewById(R.id.web_order_scrollview);
		topGridView = (LSTopGridView)findViewById(R.id.top_gridview);
		bottomGridView = (LSTopGridView)findViewById(R.id.bottom_gridview);
	}
	
	private void getSuperItems() {
		OrderUtil.getSuperItems(OrderByCheckCostActivity.this, new OnRequestCompleteListener() {
			
			@Override
			public void onRequestSuccess(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				dBHelper.deleteAllSuperItems();
				superItemsList.clear();
				upList.clear();
				downList.clear();
				dBHelper.saveSuperItemsFromJSONArray(jsonArray, superItemsList);
				for (int i = 0; i < 16; i++) {
					upList.add(superItemsList.get(i));
				}
				for (int j = 16; j < superItemsList.size(); j++) {
					downList.add(superItemsList.get(j));
				}
				superItemAdapter.notifyDataSetChanged();
				downItemAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onRequestFail() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "网络发生错误", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setSuperItems() {
		readSuperItemsCache();
		getSuperItems();
		superItemAdapter.notifyDataSetChanged();
		downItemAdapter.notifyDataSetChanged();
	}
	
	private void readSuperItemsCache() {
		superItemsList.clear();
		for (SuperItem superItem : dBHelper.loadAllSuperItems()) {
			superItemsList.add(superItem);
		}
		if (superItemsList.size() <= 0) {
			upList = new ArrayList<SuperItem>();
			downList = new ArrayList<SuperItem>();
		}
		else {
			upList = new ArrayList<SuperItem>(superItemsList.subList(0, 16));
			downList = new ArrayList<SuperItem>(superItemsList.subList(16, superItemsList.size()));
		}
		superItemAdapter = new SuperItemAdapter(upList, OrderByCheckCostActivity.this);
		downItemAdapter = new SuperItemAdapter(downList, OrderByCheckCostActivity.this);
		bottomGridView.setAdapter(downItemAdapter);
		topGridView.setAdapter(superItemAdapter);
		topGridView.setOnItemClickListener(new TopGridViewOnClickListener());
		bottomGridView.setOnItemClickListener(new BottomGridViewOnClickListener());
	}
	
	class TopGridViewOnClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(OrderByCheckCostActivity.this, DetailRepairActivity.class);
			intent.putExtra("category_id", upList.get(arg2).getSuperItemId());
			intent.putExtra("item_name", upList.get(arg2).getSuperItemName());
			intent.putExtra("pic_url", upList.get(arg2).getSuperItemImageUrl());
			startActivity(intent);
			if (version >= 5) {
				overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
			}
			if (requestCode == 1) {
				OrderByCheckCostActivity.this.finish();
			}
		}
		
	}
	
	class BottomGridViewOnClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(OrderByCheckCostActivity.this, DetailRepairActivity.class);
			intent.putExtra("category_id", downList.get(arg2).getSuperItemId());
			intent.putExtra("item_name", downList.get(arg2).getSuperItemName());
			intent.putExtra("pic_url", downList.get(arg2).getSuperItemImageUrl());
			startActivity(intent);
			if (version >= 5) {
				overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
			}
			if (requestCode == 1) {
				OrderByCheckCostActivity.this.finish();
			}
		}
		
	}
	
	private void initViewPager() {
    	imageCycleView = (ImageCycleView)findViewById(R.id.ad_viewpager_select_page);
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
			ImageLoader.getInstance().displayImage(imageURL, imageView);// 此处本人使用了ImageLoader对图片进行加装！
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		imageCycleView.pushImageCycle();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		imageCycleView.pushImageCycle();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		imageCycleView.startImageCycle();
	}
	
	
}
