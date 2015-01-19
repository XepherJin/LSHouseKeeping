package com.retropoktan.lshousekeeping.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.adapter.AddPhotoAdapter;
import com.retropoktan.lshousekeeping.application.LSApplication;
import com.retropoktan.lshousekeeping.constant.UrlConst;
import com.retropoktan.lshousekeeping.dao.OrderItemCache;
import com.retropoktan.lshousekeeping.net.CutPhotoUtil;
import com.retropoktan.lshousekeeping.net.FileUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil;
import com.retropoktan.lshousekeeping.net.OrderUtil.OnOrderRequestCompleteListener;
import com.retropoktan.lshousekeeping.net.TimeUtil;
import com.retropoktan.lshousekeeping.net.UploadPhotoUtil;
import com.retropoktan.lshousekeeping.net.UploadPhotoUtil.OnUploadCompleteListener;
import com.retropoktan.lshousekeeping.view.ChooseDatePickerDialog;
import com.retropoktan.lshousekeeping.view.ChooseTimePickerDialog;
import com.retropoktan.lshousekeeping.view.PriceTextView;
import com.retropoktan.lshousekeeping.view.ProgressHUD;

public class OrderByWebActivity extends BaseActivity{

	private HorizontalListView horizontalListView;
	
	private Button submitOrderButton;
	
	private EditText nameEditText;
	private EditText phoneEditText;
	private EditText addressEditText;
	private TextView itemTextView;
	private TextView chooseTimeTextView;
	private EditText psEditText;
	private List<File> photoFileList;
	private static final int REQUEST_CODE = 4;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1; // 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2; // 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3; // 结果
	
	private PopupWindow popupWindow = null;
	private LinearLayout ll_popup;
	
	private String repairItem;
	private String orderId;
	private String timeStamp;
	
	private PriceTextView priceTextView;
	
	private float totalPrice;
	
	private int photoUploadSuccessNum;
	
	private ProgressHUD progressHUD;
	private CutPhotoUtil cutPhotoUtil;
	
	private Button cameraButton;
	private Button cancelButton;
	private Button photoButton;
	
	private Handler handler;
	
	private List<Bitmap> mBitmapList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(R.string.order_by_web_title);
		setContentView(R.layout.order_by_web_activity);
		setRightButtonShown();
		mBitmapList = new ArrayList<Bitmap>();
		photoFileList = new ArrayList<File>();
		cutPhotoUtil = new CutPhotoUtil();
		Intent intent = getIntent();
		totalPrice = OrderItemCache.getInstance().getPrice();
		getItem(intent);
		getPrice(intent);
		initViews();
		initPopupWindow();
	}
	
	private void getPrice(Intent intent) {
		if (intent.getStringExtra("price") == null) {
			totalPrice += 0;
		}
		else {
			totalPrice += Float.parseFloat(intent.getStringExtra("price"));
		}
	}
	
	private void getItem(Intent intent) {
		if (OrderItemCache.getInstance().getOrderItem() == null || OrderItemCache.getInstance().getOrderItem().equals("")) {
			if (intent.getStringExtra("title") == null || intent.getStringExtra("title").equals("")) {
			}
			else {
				repairItem = intent.getStringExtra("title");
			}
		}
		else {
			
			repairItem = OrderItemCache.getInstance().getOrderItem();
			if (intent.getStringExtra("title") == null || intent.getStringExtra("title").equals("")) {
			}
			else {
				repairItem = repairItem + "," + intent.getStringExtra("title");
			}
		}
	}
	
	private void initViews() {
		nameEditText = (EditText)findViewById(R.id.name_in_order_form_edittext);
		phoneEditText = (EditText)findViewById(R.id.phone_in_order_form_edittext);
		addressEditText = (EditText)findViewById(R.id.address_in_order_form_edittext);
		chooseTimeTextView = (TextView)findViewById(R.id.time_in_order_form_edittext);
		itemTextView = (TextView)findViewById(R.id.repair_item_in_order_form_edittext);
		priceTextView = (PriceTextView)findViewById(R.id.total_price_textview);
		priceTextView.setTotalPrice(String.valueOf(totalPrice));
		nameEditText.setText(OrderItemCache.getInstance().getName());
		phoneEditText.setText(OrderItemCache.getInstance().getPhone());
		addressEditText.setText(OrderItemCache.getInstance().getAddress());
		itemTextView.setText(repairItem);
		itemTextView.setOnClickListener(new OrderItemOnClickListener());
		chooseTimeTextView.setOnClickListener(new OrderItemOnClickListener());
		psEditText = (EditText)findViewById(R.id.ps_in_order_form_edittext);
		psEditText.setText(OrderItemCache.getInstance().getPs());
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearAllInputs();
			}
		});
		submitOrderButton = (Button)findViewById(R.id.submit_order_button);
		submitOrderButton.setOnClickListener(new SubmitOrderButtonOnClickListener());
		rightBtn.setText("清空");
		horizontalListView = (HorizontalListView)findViewById(R.id.add_photo_listview_in_order_form);
		final AddPhotoAdapter addPhotoAdapter = new AddPhotoAdapter(mBitmapList, OrderByWebActivity.this);
		horizontalListView.setAdapter(addPhotoAdapter);
		horizontalListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == mBitmapList.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(OrderByWebActivity.this,R.anim.popupwindow_translate_in));
					popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.order_by_web_activity, null), Gravity.BOTTOM, 0, 0);
				}
			}
			
		});
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					photoUploadSuccessNum += 1;
					Log.d("photo_success_num", photoUploadSuccessNum + "");
					if (photoUploadSuccessNum == photoFileList.size()) {
						progressHUD.dismiss();
						Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(OrderByWebActivity.this, OrderResultActivity.class);
						intent.putExtra("result", getResources().getString(R.string.order_success));
						startActivity(intent);
						if (version >= 5) {
							overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
						}
						OrderByWebActivity.this.finish();
					}
					break;
				case 10:
					addPhotoAdapter.notifyDataSetChanged();
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     * 
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     * 
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
	
	private void initPopupWindow() {
		popupWindow = new PopupWindow(this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout)view.findViewById(R.id.ll_popup);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setContentView(view);
		RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.parent);
		cameraButton = (Button)view.findViewById(R.id.item_popupwindows_camera);
		photoButton = (Button)view.findViewById(R.id.item_popupwindows_Photo);
		cancelButton = (Button)view.findViewById(R.id.item_popupwindows_cancel);
		relativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});
		cameraButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePhoto();
				popupWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});
		photoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				choosePhoto();
				popupWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupWindow.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}
	
	private void choosePhoto() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, null);
		galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		try {
			startActivityForResult(galleryIntent, PHOTO_REQUEST_GALLERY);
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void takePhoto() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用拍照
		cutPhotoUtil = new CutPhotoUtil();
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cutPhotoUtil.tempFile));
		try {
			startActivityForResult(cameraIntent, PHOTO_REQUEST_TAKEPHOTO);
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void clearAllInputs() {
		nameEditText.setText("");
		phoneEditText.setText("");
		addressEditText.setText("");
		psEditText.setText("");
		itemTextView.setText("");
		chooseTimeTextView.setText("");
		priceTextView.setTotalPrice("0");
		OrderItemCache.getInstance().clearAll();
		mBitmapList.clear();
		Message msg = new Message();
		msg.what = 10;
		handler.sendMessage(msg);
	}
	
	private void chooseOrderTime() {
		final Calendar calendar = Calendar.getInstance(Locale.CHINA);
		final int hour;
		calendar.setTimeInMillis(System.currentTimeMillis());
		if (ChooseTimePickerDialog.getRoundedMinute(calendar.get(Calendar.MINUTE) + ChooseTimePickerDialog.TIME_PICKER_INTERVAL) >= 60) {
			hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
		}
		else {
			hour = calendar.get(Calendar.HOUR_OF_DAY);
		}
		ChooseDatePickerDialog chooseDatePickerDialog = new ChooseDatePickerDialog(OrderByWebActivity.this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, final int year, final int monthOfYear,
					final int dayOfMonth) {
				// TODO Auto-generated method stub
				chooseTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
				ChooseTimePickerDialog timePickerDialog = new ChooseTimePickerDialog(OrderByWebActivity.this, new ChooseTimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						if (minute == 0) {
							chooseTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":0" + minute);
						}
						else {
							chooseTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
						}
					}
					
				}, hour, ChooseTimePickerDialog.getRoundedMinute(calendar.get(Calendar.MINUTE) + ChooseTimePickerDialog.TIME_PICKER_INTERVAL), true);
				timePickerDialog.show();
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
		chooseDatePickerDialog.show();
	}
	
	private boolean isOrderFormCompleted() {
		if ((phoneEditText.getText().toString().trim().length() != 11) || nameEditText.getText().toString().trim().equals("") ||itemTextView.getText().toString().trim().equals("") || addressEditText.getText().toString().trim().equals("") || chooseTimeTextView.getText().toString().trim().equals("")) {
			return false;
		}
		return true;
	}
	
	private void submitOrder() {
		OrderItemCache.getInstance().setName(nameEditText.getText().toString().trim());
		OrderItemCache.getInstance().setAddress(addressEditText.getText().toString());
		OrderItemCache.getInstance().setPhone(phoneEditText.getText().toString().trim());
		OrderItemCache.getInstance().setPs(psEditText.getText().toString());
		OrderItemCache.getInstance().setPrice(totalPrice);
		OrderItemCache.getInstance().setOrderItem(repairItem);
		LSApplication.getInstance().setUserName(nameEditText.getText().toString().trim());
		LSApplication.getInstance().setUserAddress(addressEditText.getText().toString());
		LSApplication.getInstance().setUserPhoneNum(phoneEditText.getText().toString().trim());
		timeStamp = TimeUtil.getTimeStampFromString(chooseTimeTextView.getText().toString());
		progressHUD = ProgressHUD.show(OrderByWebActivity.this, "提交预约中", true);
		OrderUtil.requestOrder(OrderByWebActivity.this, itemTextView.getText().toString().trim(), nameEditText.getText().toString().trim(), addressEditText.getText().toString().trim(), 1, phoneEditText.getText().toString().trim(), timeStamp, psEditText.getText().toString(), new OnOrderRequestCompleteListener() {
			
			@Override
			public void onRequestSuccess(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				try {
					orderId = jsonObject.getString("appointmentid");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (photoFileList.isEmpty()) {
					progressHUD.dismiss();
					Intent intent = new Intent(OrderByWebActivity.this, OrderResultActivity.class);
					intent.putExtra("result", getResources().getString(R.string.order_success));
					startActivity(intent);
					if (version >= 5) {
						overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
					}
					OrderByWebActivity.this.finish();
				}
				else {
					Log.d("photo_file", photoFileList.toString());
					for (int i = 1; i <= photoFileList.size(); i++) {
						try {
							UploadPhotoUtil.uploadFile(i, photoFileList.get(i - 1), UrlConst.AppointmentPicUrl, orderId, LSApplication.getInstance().getUserPhoneNum(), new OnUploadCompleteListener() {
								
								@Override
								public void onUploadSuccess() {
									// TODO Auto-generated method stub
									progressHUD.dismiss();
									Message message = new Message();
									message.what = 1;
									handler.sendMessage(message);
								}
								
								@Override
								public void onUploadFail() {
									// TODO Auto-generated method stub
									progressHUD.dismiss();
									Toast.makeText(getApplicationContext(), "上传失败，请尝试再次提交！", Toast.LENGTH_SHORT).show();
								}
							});
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void onRequestFail() {
				// TODO Auto-generated method stub
				progressHUD.dismiss();
				Intent intent = new Intent(OrderByWebActivity.this, OrderResultActivity.class);
				intent.putExtra("result", getResources().getString(R.string.order_failed));
				startActivity(intent);
			}
		});
	}
	
	class OrderItemOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.time_in_order_form_edittext:
				chooseOrderTime();
				break;
			case R.id.repair_item_in_order_form_edittext:
				chooseRepairItem();
				break;
			default:
				break;
			}
		}
		
	}
	
	private void chooseRepairItem() {
		OrderItemCache.getInstance().setName(nameEditText.getText().toString().trim());
		OrderItemCache.getInstance().setAddress(addressEditText.getText().toString());
		OrderItemCache.getInstance().setPhone(phoneEditText.getText().toString().trim());
		OrderItemCache.getInstance().setPrice(totalPrice);
		OrderItemCache.getInstance().setOrderItem(repairItem);
		OrderItemCache.getInstance().setPs(psEditText.getText().toString());
		Intent intent = new Intent(OrderByWebActivity.this, OrderByCheckCostActivity.class);
		intent.putExtra("request_code", 1);
		startActivity(intent);
		if (version >= 5) {
			overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
		}
		OrderByWebActivity.this.finish();
	}
	
	class SubmitOrderButtonOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (isOrderFormCompleted()) {
				if (!isFirstTimeToOrder()) {
					submitOrder();
				}
				else {
					Intent intent = new Intent(OrderByWebActivity.this, CheckSMSActivity.class);
					intent.putExtra("phone", phoneEditText.getText().toString().trim());
					startActivityForResult(intent, REQUEST_CODE);
					if (version >= 5) {
						overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
					}
				}
			}
			else {
				Toast.makeText(getApplicationContext(), "表单填写不完整！", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			cutPhotoUtil.startPhotoZoom(Uri.fromFile(cutPhotoUtil.tempFile), 150);
			startActivityForResult(cutPhotoUtil.intent, PHOTO_REQUEST_CUT);
			break;
		case PHOTO_REQUEST_GALLERY:
			if (data != null) {
				cutPhotoUtil.startPhotoZoom(data.getData(), 150);
				startActivityForResult(cutPhotoUtil.intent, PHOTO_REQUEST_CUT);
			}
			break;
		case PHOTO_REQUEST_CUT:
			if (data != null) {
				cutPhotoUtil.setPicToView(data);
				mBitmapList.add(cutPhotoUtil.getBitmap());
				photoFileList.add(FileUtil.saveBitmap(cutPhotoUtil.getBitmap()));
				Message msg = new Message();
				msg.what = 10;
				handler.sendMessage(msg);
			}
			break;
		case REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				submitOrder();
			}
			break;
		}
	}

	private boolean isFirstTimeToOrder() {
		if (LSApplication.getInstance().getToken() == null || LSApplication.getInstance().getToken().toString().trim().equals("")) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (nameEditText.getText().toString().trim().equals("") && phoneEditText.getText().toString().trim().equals("")
			&& addressEditText.getText().toString().trim().equals("") && psEditText.getText().toString().trim().equals("")) {
				finish();
				if (version >= 5) {
					overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
				}
		}
		else {
			new AlertDialog.Builder(OrderByWebActivity.this).setTitle("退出预约")
			.setMessage("确认退出预约？")
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					OrderItemCache.getInstance().setName(nameEditText.getText().toString().trim());
					OrderItemCache.getInstance().setAddress(addressEditText.getText().toString());
					OrderItemCache.getInstance().setPhone(phoneEditText.getText().toString().trim());
					OrderItemCache.getInstance().setPs(psEditText.getText().toString());
					OrderByWebActivity.this.finish();
					if (version >= 5) {
						overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
					}
				}
			}).setNegativeButton("取消", null).show();
		}
	}
}
