package com.retropoktan.lshousekeeping.adapter;

import com.retropoktan.lshousekeeping.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderByPhoneAdapter extends BaseAdapter{

	public static final String TAG = OrderByPhoneAdapter.class.getSimpleName();
	public LayoutInflater layoutInflater;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.order_by_phone_item, null);
			viewHolder.cityImageview = (ImageView)convertView.findViewById(R.id.order_by_phone_item_imageview);
			viewHolder.cityNameTextView = (TextView)convertView.findViewById(R.id.order_by_phone_item_name_textview);
			viewHolder.cityPhoneTextView = (TextView)convertView.findViewById(R.id.order_by_phone_item_phone_textview);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		return convertView;
	}
	
	private static class ViewHolder{
		private ImageView cityImageview;
		private TextView cityNameTextView, cityPhoneTextView;
	}

}
