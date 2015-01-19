package com.retropoktan.lshousekeeping.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class PriceTextView extends TextView{

	public PriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public PriceTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PriceTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setPrice(String string) {
		setText(Html.fromHtml("<font color=\'#ff8400\'>" + string + "  ")); //</font><font color=\'#666666\'> 元</font>
	}
	
	public void setTotalPrice(String string) {
		setText(Html.fromHtml("</font><font color=\'#666666\'>总价： </font><font color=\'#ff8400\'>" + string + "</font><font color=\'#666666\'>  元</font>"));
	}
}
