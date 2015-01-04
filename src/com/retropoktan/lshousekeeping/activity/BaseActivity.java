package com.retropoktan.lshousekeeping.activity;

import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.application.LSApplication;

import de.greenrobot.dao.internal.FastCursor;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity implements OnClickListener{

	protected LSApplication mApplication;
	protected ImageView leftBtn, netBtn;
	protected View titleView;
	protected TextView titleTextView;
	
	protected TextView rightBtn;
	
	private TextView refreshTip;
	
	private TextView chooseCityTextView; // show current city
	
	private LinearLayout mLinearLayout;
	
	private LinearLayout chooseCityLayout; //choose city's layout,only shown in main page 
	
	private View mView, actionBar, refreshView, netView;
	/**    是否刷新    **/
	private boolean isRefreshing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication = (LSApplication)getApplication();
		mLinearLayout = new LinearLayout(this);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		initActionBar();
		super.setContentView(mLinearLayout);
	}
	
	protected void initActionBar() {
		actionBar = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
		actionBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mLinearLayout.addView(actionBar);
		// 刷新模块
		refreshView = actionBar.findViewById(R.id.refresh_view);
		refreshTip = (TextView)refreshView.findViewById(R.id.refresh_tip);
		// choose city module
		chooseCityLayout = (LinearLayout)actionBar.findViewById(R.id.title_choose_city_layout);
		chooseCityTextView = (TextView)actionBar.findViewById(R.id.current_city_textview);
		// 网络模块
		netView = actionBar.findViewById(R.id.net_view);
		netBtn = (ImageView)netView.findViewById(R.id.net_state_cancel);
		netBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				netView.setVisibility(View.GONE);
			}
		});
		// 标题模块
		titleView = actionBar.findViewById(R.id.title_view);
		leftBtn = (ImageView)titleView.findViewById(R.id.title_leftbtn);
		leftBtn.setOnClickListener(this);
		rightBtn = (TextView)titleView.findViewById(R.id.title_rightbtn);
		titleTextView = (TextView)titleView.findViewById(R.id.title_normal);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
		
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(layoutResID, null);
		setContentView(view);
	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		if (mView != null) {
			mLinearLayout.removeView(mView);
		}
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mView = view;
		mLinearLayout.addView(mView);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		// TODO Auto-generated method stub
		setContentView(view);
	}

	@Override
	public View findViewById(int id) {
		// TODO Auto-generated method stub
		View view = mView.findViewById(id);
		return view == null ? super.findViewById(id) : view;
	}

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		titleTextView.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		// TODO Auto-generated method stub
		titleTextView.setText(titleId);
	}

	@Override
	public void setTitleColor(int textColor) {
		// TODO Auto-generated method stub
		titleTextView.setTextColor(textColor);
	}
	
	public void setBackButtonHidden() {
		leftBtn.setVisibility(View.INVISIBLE);
		leftBtn.setClickable(false);
	}
	
	public void setBackButtonGone() {
		leftBtn.setVisibility(View.GONE);
		leftBtn.setClickable(false);
	}
	
	public void setRightButtonShown() {
		rightBtn.setVisibility(View.VISIBLE);
		leftBtn.setClickable(true);
	}
	
	public void setChangeCityShown() {
		setBackButtonGone();
		chooseCityLayout.setVisibility(View.VISIBLE);
	}
	
	protected final void showRefresh(int tipId) {
		if (canRefresh()) {
			refreshTip.setText(tipId);
			refreshView.setVisibility(View.VISIBLE);
			isRefreshing = true;
		}
	}
	
	protected final void showRefresh(String tip) {
		if (canRefresh()) {
			refreshTip.setText(tip);
			refreshView.setVisibility(View.VISIBLE);
			isRefreshing = true;
		}
	}
	
	private final boolean isRefreshing() {
		if (isRefreshing) {
			Toast.makeText(this, "正在刷新", Toast.LENGTH_SHORT).show();
		}
		return isRefreshing;
	}
	
	protected final boolean canRefresh() {
		return !isRefreshing() && isNetworkConnected();
	}
	
	private final boolean isNetworkConnected() {
		boolean result = mApplication.isNetworkConnected();
		if (!result) {
			netView.setVisibility(View.VISIBLE);
		}
		else {
			netView.setVisibility(View.GONE);
		}
		return result;
	}
	
	protected final void OnRefreshComplete() {
		refreshView.setVisibility(View.GONE);
		isRefreshing = false;
	}
}
