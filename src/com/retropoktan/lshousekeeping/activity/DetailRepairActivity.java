package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.retropoktan.lshousekeeping.R;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DetailRepairActivity extends BaseActivity{

	private ListView detailRepairListView;
	private List<HashMap<String, Object>> mData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_repair_activity);
		setRightButtonShown();
		initViews();
	}

	private void initViews() {
		detailRepairListView = (ListView)findViewById(R.id.detail_repair_listview);
		mData = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < 15; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("test", "管子炸了肿么办");
			mData.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, mData, R.layout.detail_repair_item, new String[]{"test"}, new int[]{R.id.detail_repair_title_textview});
		detailRepairListView.setAdapter(simpleAdapter);
	}
}
