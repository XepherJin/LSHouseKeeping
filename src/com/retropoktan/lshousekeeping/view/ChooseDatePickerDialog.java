package com.retropoktan.lshousekeeping.view;

import android.app.DatePickerDialog;
import android.content.Context;

public class ChooseDatePickerDialog extends DatePickerDialog{

	public ChooseDatePickerDialog(Context context, int theme,
			OnDateSetListener listener, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, listener, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
	}

	public ChooseDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		//super.onStop();
	}

}
