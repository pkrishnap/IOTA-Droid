package com.example.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {
	
	private static final String TAG = "LocationReceiver";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Location loc = (Location)arg1.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
		if (loc !=null) {
			onLocationUpdate(arg0,loc);
			return;
		}
		
		if(arg1.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){
			boolean enabled = arg1.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
			onProviderEnabledChanged(enabled);
		}
		

	}

	public void onProviderEnabledChanged(boolean enabled) {
		Log.i(TAG, "Provider Update Received");
		
	}

	public void onLocationUpdate(Context arg0, Location loc) {
		Log.i(TAG, "LocationUpdate Received");
		
	}

}
