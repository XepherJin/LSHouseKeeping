package com.retropoktan.lshousekeeping.net;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONArray;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.retropoktan.lshousekeeping.application.LSApplication;

public class UploadPhotoUtil {

    /** 
     *
     * @param url 
     *            服务端接收URL 
     * @throws Exception 
     */ 
	public static void uploadFile(int picIndex, File file, String url, String appointmentId, String consumer, final OnUploadCompleteListener onUploadCompleteListener) throws Exception {  
		if (file.exists() && file.length() > 0) {  
			RequestParams params = new RequestParams();  
			params.put("token", LSApplication.getInstance().getToken());
			params.put("consumer", consumer);
			params.put("file", file);
			params.put("appointmentid", appointmentId);
			params.put("picindex", String.valueOf(picIndex));
			// 上传文件  
			HttpUtil.post(url, params, new AsyncHttpResponseHandler() {  
				@Override  
				public void onSuccess(int statusCode, Header[] headers,  
					byte[] responseBody) {  
					onUploadCompleteListener.onUploadSuccess();
		}
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					onUploadCompleteListener.onUploadFail();
			}

			      @Override  
			      public void onProgress(int bytesWritten, int totalSize) {  
			          // TODO Auto-generated method stub  
			          super.onProgress(bytesWritten, totalSize);  
			          int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);  
			          // 上传进度显示  
			      }  

			      @Override  
			      public void onRetry(int retryNo) {  
			          // TODO Auto-generated method stub  
			          super.onRetry(retryNo);  
			          // 返回重试次数  
			       } 
			});
		} 
		}
	public static interface OnUploadCompleteListener{
		public void onUploadSuccess();
		public void onUploadFail();
	}
}
