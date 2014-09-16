package com.example.photogallery;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class StartUpReceiver extends BroadcastReceiver {
	
	private static final String TAG = "SartUpReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Received Broadcast Intent:" + intent.getAction());
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isAlarmOn = prefs.getBoolean(PollService.PREF_IS_ALARM_ON, false);
		PollService.setAlarmService(context, isAlarmOn);

	}

}
