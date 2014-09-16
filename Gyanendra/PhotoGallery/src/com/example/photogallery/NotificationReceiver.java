package com.example.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
	private static final String TAG = "NotificationReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Notification Receiver");
		if(getResultCode() != Activity.RESULT_OK)
			return;
		
		int resultCode = intent.getIntExtra("RESULT_CODE", 0);
		Notification notification = (Notification)intent.getParcelableExtra("NOTIFICATION");
		
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(resultCode, notification);

	}

}
