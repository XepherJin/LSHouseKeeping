package com.retropoktan.lshousekeeping.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.retropoktan.lshousekeeping.R;
import com.retropoktan.lshousekeeping.dao.SuperItem;

public class SuperItemAdapter extends BaseAdapter{

	public static final String TAG = SuperItemAdapter.class.getSimpleName();
	public LayoutInflater layoutInflater;
	public List<SuperItem> superItemsList;
	
	public SuperItemAdapter(List<SuperItem> superItemsList, Context context) {
		super();
		this.superItemsList = superItemsList;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return superItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return superItemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.web_order_item, null);
			viewHolder.superItemImageView = (ImageView)convertView.findViewById(R.id.web_order_item_imageview);
			viewHolder.superItemTextView = (TextView)convertView.findViewById(R.id.web_order_item_textview);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		SuperItem superItem = (SuperItem)superItemsList.get(position);
		ImageLoader.getInstance().displayImage(superItem.getSuperItemImageUrl(), viewHolder.superItemImageView);
		viewHolder.superItemTextView.setText(superItem.getSuperItemName());
		return convertView;
	}

	private static class ViewHolder{
		ImageView superItemImageView;
		TextView superItemTextView;
	}
}
