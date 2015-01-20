package com.retropoktan.lshousekeeping.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.retropoktan.lshousekeeping.R;

public class AddPhotoAdapter extends BaseAdapter{

	public LayoutInflater layoutInflater;
	public static final String TAG = AddPhotoAdapter.class.getSimpleName();
	public List<Bitmap> mBitmapList;
	
	public Context context;
	
	public AddPhotoAdapter(List<Bitmap> mBitmapList, Context context) {
		super();
		this.mBitmapList = mBitmapList;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mBitmapList.size() >= 4) {
			return 4;
		}
		else {
			return (mBitmapList.size() + 1);
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mBitmapList.get(position);
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
		if (position == mBitmapList.size()) {
			viewHolder.photoImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
			if (position == 4) {
				viewHolder.photoImageView.setVisibility(View.GONE);
			}
		}
		else {
			viewHolder.photoImageView.setImageBitmap(mBitmapList.get(position));
		}
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView photoImageView;
	}

}
