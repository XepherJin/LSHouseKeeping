package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.view.LSTopGridView;

public class OrderByCheckCostActivity extends BaseActivity{

	private ScrollView scrollView;
	private LSTopGridView topGridView;
	private List<HashMap<String, Object>> mData;
	
	private ViewPager adViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.order_by_check_cost_title);
		setContentView(R.layout.web_order_select);
		initViews();
	}

	private void initViews() {
		scrollView = (ScrollView)findViewById(R.id.web_order_scrollview);
		topGridView = (LSTopGridView)findViewById(R.id.top_gridview);
		mData = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < 40; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Image", R.drawable.ic_launcher);
			map.put("Text", "水电");
			mData.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, mData, R.layout.web_order_item, new String[]{"Image", "Text"}, new int[]{R.id.web_order_item_imageview, R.id.web_order_item_textview});
		topGridView.setAdapter(simpleAdapter);
	}
	
	class TopGridViewOnClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
		}
		
	}
}
