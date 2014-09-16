package com.example.runtracker;

import android.content.Context;
import android.location.Location;

public class TrackingLocationReceiver extends LocationReceiver {

	@Override
	public void onLocationUpdate(Context arg0, Location loc) {
		RunManager.get(arg0).insertLocation(loc);
	}
	

}
