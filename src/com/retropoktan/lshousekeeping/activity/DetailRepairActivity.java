package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.adapter.DetailRepairAdapter;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.dao.DetailRepairItem;
import com.retropoktan.lshousekeeping.net.JSONUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil.OnRequestCompleteListener;
import com.retropoktan.lshousekeeping.view.ProgressHUD;

public class DetailRepairActivity extends BaseActivity{

	private ListView detailRepairListView;
	private List<DetailRepairItem> detailRepairItemsList;
	private DetailRepairAdapter detailRepairAdapter;
	private String categoryId;
	private ProgressHUD progressHUD;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_repair_activity);
		Intent intent = getIntent();
		categoryId = intent.getStringExtra("category_id");
		setRightButtonShown();
		detailRepairItemsList = new ArrayList<DetailRepairItem>();
		progressHUD = ProgressHUD.show(this, "载入中", true);
		getDetailRepairItems(categoryId);
		initViews();
	}

	private void initViews() {
		rightBtn.setText("拨打电话");
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(DetailRepairActivity.this)
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
		detailRepairListView = (ListView)findViewById(R.id.detail_repair_listview);
	}
	
	private void getDetailRepairItems(String id) {
		OrderUtil.getDetailRepairItems(id, DetailRepairActivity.this, new OnRequestCompleteListener() {
			
			@Override
			public void onRequestSuccess(JSONArray jsonArray) {
				// TODO Auto-generated method stub
				JSONUtil.parseDetailRepairItemTexts(jsonArray, detailRepairItemsList);
				detailRepairAdapter = new DetailRepairAdapter(detailRepairItemsList, DetailRepairActivity.this);
				detailRepairListView.setAdapter(detailRepairAdapter);
				detailRepairListView.setOnItemClickListener(new DetailItemOnClickListener());
				progressHUD.dismiss();
			}
			
			@Override
			public void onRequestFail() {
				// TODO Auto-generated method stub
				progressHUD.dismiss();
				Toast.makeText(DetailRepairActivity.this, "网络发生错误", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	class DetailItemOnClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(DetailRepairActivity.this, OrderByWebActivity.class);
			intent.putExtra("title", detailRepairItemsList.get(arg2).getTitle());
			startActivity(intent);
			if (version >= 5) {
				overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
			}
		}
		
	}
}
