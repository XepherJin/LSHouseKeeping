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
import com.retropoktan.lshousekeeping.dao.DetailRepairItem;
import com.retropoktan.lshousekeeping.view.PriceTextView;

public class DetailRepairAdapter extends BaseAdapter{

	public static final String TAG = DetailRepairAdapter.class.getSimpleName();
	public LayoutInflater layoutInflater;
	
	public List<DetailRepairItem> detailRepairItemsList;
	
	public String picUrl;
	
	public int[] checkList;
	
	public DetailRepairAdapter(List<DetailRepairItem> detailRepairItemsList, Context context, String picUrl) {
		super();
		this.detailRepairItemsList = detailRepairItemsList;
		this.layoutInflater = LayoutInflater.from(context);
		this.picUrl = picUrl;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detailRepairItemsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return detailRepairItemsList.get(position);
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
		DetailRepairItem detailRepairItem = (DetailRepairItem)detailRepairItemsList.get(position);
		viewHolder.repairItemTitleTextView.setText(detailRepairItem.getTitle());
		viewHolder.repairItemPriceTextView.setPrice(String.valueOf(detailRepairItem.getPrice()));
		viewHolder.repairItemContentTextView.setText(detailRepairItem.getContent());
		ImageLoader.getInstance().displayImage(picUrl, viewHolder.repairItemImageView);
		
		return convertView;
	}

	private static class ViewHolder{
		private ImageView repairItemImageView;
		private TextView repairItemTitleTextView, repairItemContentTextView;
		private PriceTextView repairItemPriceTextView;
	}
}
