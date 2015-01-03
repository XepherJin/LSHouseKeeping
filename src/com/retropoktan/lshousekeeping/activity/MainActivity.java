package com.retropoktan.lshousekeeping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.retropoktan.lshousekeeping.R;


public class MainActivity extends BaseActivity{

	private LinearLayout orderByPhoneLayout;
	private LinearLayout orderByWebLayout;
	private LinearLayout orderByCheckCostLayout;
	private ViewPager adViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setChangeCityShown();
        setRightButtonShown();
        initButtons();
    }

    private void initButtons() {
    	orderByPhoneLayout = (LinearLayout)findViewById(R.id.order_by_phone_layout);
    	orderByWebLayout = (LinearLayout)findViewById(R.id.order_by_web_layout);
    	orderByCheckCostLayout = (LinearLayout)findViewById(R.id.order_by_check_cost_layout);
    	orderByPhoneLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	orderByWebLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	orderByCheckCostLayout.setOnClickListener(new MainPageButtonOnClickListener());
    	rightBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
    	rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "好热呵呵", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    private void initViewPager() {
    	adViewPager = (ViewPager)findViewById(R.id.ad_viewpager_main_page);
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}


	class MainPageButtonOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.order_by_phone_layout:
				Intent phoneIntent = new Intent(MainActivity.this, OrderByPhoneActivity.class);
				startActivity(phoneIntent);
				break;
			case R.id.order_by_web_layout:
				Intent WebIntent = new Intent(MainActivity.this, OrderByWebActivity.class);
				startActivity(WebIntent);
				break;
			case R.id.order_by_check_cost_layout:
				Intent CheckCostIntent = new Intent(MainActivity.this, OrderByCheckCostActivity.class);
				startActivity(CheckCostIntent);
				break;
			default:
				break;
			}
		}
    	
    }
}
