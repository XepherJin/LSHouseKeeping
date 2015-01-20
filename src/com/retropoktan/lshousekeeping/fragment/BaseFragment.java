package com.retropoktan.lshousekeeping.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.retropoktan.lshousekeeping.activity.BaseFragmentActivity;

public class BaseFragment extends Fragment{

	public static final int TYPE_MAIN = 1;
	public static final int TYPE_SETTING = 2;
	
	public int TYPE;
	
	protected BaseFragmentActivity activity;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		activity = (BaseFragmentActivity)getActivity();
	}
	
	
}
