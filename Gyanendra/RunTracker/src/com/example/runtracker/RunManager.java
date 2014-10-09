package com.example.runtracker;

import com.example.runtracker.RunDatabaseHelper.LocationCursor;
import com.example.runtracker.RunDatabaseHelper.RunCursor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class RunManager {
	
	private static final String TAG = "RunManager";
	private static final String PREF_FILE="runs";
	private static final String PREF_CURRENT_RUN_ID="Runmanager.currentRunId";
	
	public static final String ACTION_LOCATION ="com.example.runtracker.ACTION_LOCATION";
	
	private static RunManager mRunManager;
	private LocationManager mLocationManager;
	private Context mContext;
	private RunDatabaseHelper mHelper;
	private long mCurrentId;
	private SharedPreferences mSharedPrefs;
	
	
	private RunManager(Context appContext){
		mContext = appContext;
		
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		mHelper = new RunDatabaseHelper(mContext);
		mSharedPrefs = mContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
		mCurrentId = mSharedPrefs.getLong(PREF_CURRENT_RUN_ID, -1);
	}
	
	public static RunManager get(Context c){
		if (mRunManager==null) {
			mRunManager = new RunManager(c);
			
		}
		return mRunManager;
			
	}
	
	private PendingIntent getLocationPendingIntent(boolean shouldCreate){
		
		Intent broadcast = new Intent(ACTION_LOCATION);
		
		int flags = shouldCreate ? 0:PendingIntent.FLAG_NO_CREATE;
		
		
		
		return PendingIntent.getBroadcast(mContext, 0, broadcast, flags);
		
	}
	
	public void startLocationUpdates(){
		String provider = LocationManager.GPS_PROVIDER;
		
		PendingIntent pi = getLocationPendingIntent(true);
		mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
	}
	
	public void stopLocationUpdates() {
		PendingIntent pi = getLocationPendingIntent(false);
		if (pi!=null) {
			mLocationManager.removeUpdates(pi);
			pi.cancel();
			
		}
	}
	
	public boolean isTrackingRun(){
		return getLocationPendingIntent(false) != null;
	}
	
	public Run startNewRun(){
		Run run = insertRun();
		startTrackingRun(run);
		
		return null;
		
	}

	private Run insertRun() {
		Run run = new Run();
		run.setmID(mHelper.InsertRun(run));
		return run;
	}

	public void startTrackingRun(Run run) {
		mCurrentId = run.getmID();
		mSharedPrefs.edit().putLong(PREF_CURRENT_RUN_ID, mCurrentId).commit();
		startLocationUpdates();
		
	}
	
	public void stopRun(){
		stopLocationUpdates();
		mCurrentId =-1;
		mSharedPrefs.edit().remove(PREF_CURRENT_RUN_ID).commit();
	}
	
	public void insertLocation(Location location){
		if (mCurrentId != -1){
			mHelper.InsertLocation(mCurrentId, location);
		} else {
			Log.e(TAG, "Location received but no run working");
		}
	}
	
	public RunCursor queryRuns(){
		return mHelper.queryRuns();
	}
	
	public Run getRun(long runId){
		Run run = null;
		RunCursor cursor = mHelper.queryRun(runId);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			run = cursor.getRun();
		}
		
		cursor.close();
		return run;
		
	}
	
	public boolean isTrackingRun(Run run){
		return run != null && run.getmID() == mCurrentId;
	}
	
	
	public Location getLocation(long runId){
		Location location = null;
		LocationCursor cursor = mHelper.queryLastlocation(runId);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			location= cursor.getlocation();
		}
		
		cursor.close();
		return location;
		
	}
	


	
	

}