package com.retropoktan.lshousekeeping.adapter;

import java.util.List;

import com.retropoktan.lshousekeeping.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AddPhotoAdapter extends BaseAdapter{

	public LayoutInflater layoutInflater;
	public static final String TAG = AddPhotoAdapter.class.getSimpleName();
	public List<Drawable> mImageViewList;
	
	

	public AddPhotoAdapter(List<Drawable> mImageViewList, Context context) {
		super();
		this.mImageViewList = mImageViewList;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageViewList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mImageViewList.get(position);
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
			convertView = layoutInflater.inflate(R.layout.add_photo_listview_item, null);
			viewHolder.photoImageView = (ImageView)convertView.findViewById(R.id.photo_item_in_order_form);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.photoImageView.setImageResource(R.drawable.ic_launcher);
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView photoImageView;
	}

}
