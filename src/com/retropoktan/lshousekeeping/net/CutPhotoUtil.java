package com.retropoktan.lshousekeeping.net;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class CutPhotoUtil{
	
	public Intent intent = new Intent("com.android.camera.action.CROP");
	
	private Drawable drawable;
	
	private Bitmap bitmap;

	public File tempFile = new File(Environment.getExternalStorageDirectory() + "/YiXiuGe_Image/"+ getPhotoFileName());
	
	public CutPhotoUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void startPhotoZoom(Uri uri, int size) {
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true"); //crop为true是设置在开启的intent中设置显示的view可以剪裁
		
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		
		//startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	public String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	
	@SuppressWarnings("deprecation")
	public void setPicToView(Intent picData) {
		Bundle bundle = picData.getExtras();
		if (bundle != null) {
			bitmap = bundle.getParcelable("data");
			drawable = new BitmapDrawable(bitmap);
			setBitmap(bitmap);
			setDrawable(drawable);
		}
	}
	
	public Drawable getDrawable() {
		return drawable;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	
	
}
