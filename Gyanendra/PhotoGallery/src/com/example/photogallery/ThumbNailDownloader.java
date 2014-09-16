package com.example.photogallery;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ThumbNailDownloader<Token> extends HandlerThread {
	
	private static final String TAG = "ThumbNailDownloader";
	private static final int MESSAGE_DOWNLOAD = 0;
	private Handler mHandler;
	private Handler mResponseHandler;
	
	private Listner<Token> mListner;
	
	public interface Listner<Token>{
		void onThumbnailDownloaded(Token token,Bitmap thumbnail);
	}
	
	public void setListner(Listner<Token> listner){
		mListner = listner;
	}
	
	Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
	
	@Override
	protected void onLooperPrepared() {
		// TODO Auto-generated method stub
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==MESSAGE_DOWNLOAD){
					@SuppressWarnings("unchecked")
					Token token = (Token) msg.obj;
					handleRequest(token);
				}
				
				
			}
			
		};
		
	}
	

	protected void handleRequest(final Token token) {
		// TODO Auto-generated method stub
		
		final String url = requestMap.get(token);
		
		if (url == null) {
			return;
			
		}
		
		byte[] bitmapBYTES;
		try {
			bitmapBYTES = new FlickerFetcher().getUrlBytes(url);
			final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBYTES, 0, bitmapBYTES.length);
			mResponseHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (requestMap.get(token)!= url) {
						return;
					}
					
				mListner.onThumbnailDownloaded(token, bitmap);
					
				}
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Error Downloading Image");
		}
		
		
		
		
		
	}
	
	public void clearQueues(){
		mHandler.removeMessages(MESSAGE_DOWNLOAD);
		requestMap.clear();
	}


	public ThumbNailDownloader(Handler responsehandler) {
		super(TAG);
		mResponseHandler = responsehandler;
		
	}
	
	public void queueThumbnail(Token token, String url){
		Log.i(TAG,"Got URL " + url);
		requestMap.put(token, url);
		mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
	}


}
