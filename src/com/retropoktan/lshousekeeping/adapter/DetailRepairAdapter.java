package com.retropoktan.lshousekeeping.adapter;

import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.view.PriceTextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailRepairAdapter extends BaseAdapter{

	public static final String TAG = DetailRepairAdapter.class.getSimpleName();
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
			convertView = layoutInflater.inflate(R.layout.detail_repair_item, null);
			viewHolder.repairItemImageView = (ImageView)convertView.findViewById(R.id.repair_item_imageview);
			viewHolder.repairItemTitleTextView = (TextView)convertView.findViewById(R.id.detail_repair_title_textview);
			viewHolder.repairItemContentTextView = (TextView)convertView.findViewById(R.id.detail_repiar_content_textview);
			viewHolder.repairItemPriceTextView = (PriceTextView)convertView.findViewById(R.id.detail_repiar_content_cost_textview);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		return convertView;
	}

	private static class ViewHolder{
		private ImageView repairItemImageView;
		private TextView repairItemTitleTextView, repairItemContentTextView;
		private PriceTextView repairItemPriceTextView;
	}
}
