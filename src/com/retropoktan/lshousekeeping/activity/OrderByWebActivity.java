package com.retropoktan.lshousekeeping.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.net.OrderUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil.OnOrderRequestCompleteListener;
import com.retropoktan.lshousekeeping.view.ChooseDatePickerDialog;
import com.retropoktan.lshousekeeping.view.ProgressHUD;

public class OrderByWebActivity extends BaseActivity{

	private List<HashMap<String, Object>> mData;
	private HorizontalListView horizontalListView;
	
	private Button submitOrderButton;
	
	private EditText nameEditText;
	private EditText phoneEditText;
	private EditText addressEditText;
	private TextView chooseTimeTextView;
	private EditText psEditText;
	
	private ProgressHUD progressHUD;
	
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
		nameEditText = (EditText)findViewById(R.id.name_in_order_form_edittext);
		phoneEditText = (EditText)findViewById(R.id.phone_in_order_form_edittext);
		addressEditText = (EditText)findViewById(R.id.address_in_order_form_edittext);
		chooseTimeTextView = (TextView)findViewById(R.id.time_in_order_form_edittext);
		psEditText = (EditText)findViewById(R.id.ps_in_order_form_edittext);
		
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
		nameEditText.setText("");
		phoneEditText.setText("");
		addressEditText.setText("");
		psEditText.setText("");
	}
	
	private void chooseOrderTime() {
		final Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		ChooseDatePickerDialog chooseDatePickerDialog = new ChooseDatePickerDialog(OrderByWebActivity.this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, final int year, final int monthOfYear,
					final int dayOfMonth) {
				// TODO Auto-generated method stub
				chooseTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
				TimePickerDialog timePickerDialog = new TimePickerDialog(OrderByWebActivity.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						chooseTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "  " + hourOfDay + ":" + minute);
					}
				}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
				timePickerDialog.show();
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
		chooseDatePickerDialog.show();
	}
	
	private boolean isOrderFormCompleted() {
		return true;
	}
	
	private void submitOrder() {
		if (isOrderFormCompleted()) {
			progressHUD = ProgressHUD.show(OrderByWebActivity.this, "提交预约中", true);
			OrderUtil.requestOrder(OrderByWebActivity.this, "", nameEditText.getText().toString().trim(), addressEditText.getText().toString().trim(), 1, phoneEditText.getText().toString().trim(), new OnOrderRequestCompleteListener() {
				
				@Override
				public void onRequestSuccess(int statusCode) {
					// TODO Auto-generated method stub
					progressHUD.dismiss();
				}
			});
		}
	}
	
	class OrderItemOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.time_in_order_form_edittext:
				chooseOrderTime();
				break;
			default:
				break;
			}
		}
		
	}
	
	class SubmitOrderButtonOnClickListener implements OnClickListener{

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
