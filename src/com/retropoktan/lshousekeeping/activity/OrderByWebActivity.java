package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.retropoktan.lshousekeeping.R;

public class OrderByWebActivity extends BaseActivity{

	private List<HashMap<String, Object>> mData;
	private HorizontalListView horizontalListView;
	
	private Button submitOrderButton;
	
	private int REQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.order_by_web_title);
		setContentView(R.layout.order_by_web_activity);
		setRightButtonShown();
		initViews();
	}
	
	private void initViews() {
		submitOrderButton = (Button)findViewById(R.id.submit_order_button);
		submitOrderButton.setOnClickListener(new SubmitOrderBUttonOnClickListener());
		rightBtn.setText("清空");
		mData = new ArrayList<HashMap<String,Object>>();
		horizontalListView = (HorizontalListView)findViewById(R.id.add_photo_listview_in_order_form);
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("test", R.drawable.ic_launcher);
			mData.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, mData, R.layout.add_photo_listview_item, new String[]{"test"}, new int[]{R.id.photo_item_in_order_form});
		horizontalListView.setAdapter(simpleAdapter);
		
	}

	private void clearAllInputs() {
		
	}
	
	private boolean isOrderFormCompleted() {
		return false;
	}
	
	class SubmitOrderBUttonOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (isFirstTimeToOrder()) {
				Intent intent = new Intent(OrderByWebActivity.this, OrderResultActivity.class);
				intent.putExtra("result", getResources().getString(R.string.order_failed));
				startActivity(intent);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
				finish();
			}
			else {
				Intent intent = new Intent(OrderByWebActivity.this, CheckSMSActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
				}
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			//http make order request
		}
	}

	private boolean isFirstTimeToOrder() {
		return true;
	}
}
