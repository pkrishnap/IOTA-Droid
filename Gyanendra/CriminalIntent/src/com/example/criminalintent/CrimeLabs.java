package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLabs {
	
	private static CrimeLabs sCrimeLabs;
	private ArrayList<Crime> mCrimes;
	private Context mAppContext;
	private CriminalIntentJSONSerializer mSerializer;
	private static final String TAG ="Crimelabs";
	private static final String mFileName ="Crimes.json";

	
	private CrimeLabs(Context appContext){
		
		mAppContext = appContext;
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, mFileName);
		
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<Crime>();
			Log.d(TAG, "load Failed");
		}
		
		for (int i = 0; i < 10; i++) {
			
			Crime c = new Crime();
			c.setmTitle("Crime #" + i);
			c.setmSolved(i%2 ==0);
			mCrimes.add(c);

			
		}
	}
	
	
	public static CrimeLabs get(Context c){
		
		if (sCrimeLabs == null){
			sCrimeLabs = new CrimeLabs(c.getApplicationContext());
			
		}
		
		return sCrimeLabs;
		
	}
	
	
	public ArrayList<Crime> getCrimes(){
		
		return mCrimes;
		}
	
	public Crime getCrime(UUID id){
		
		for (Crime c: mCrimes){
			if(c.getmID().equals(id))
				return c;
		}
		
		return null;
	}
	
	public void addCrime(Crime c){
		mCrimes.add(c);
	}
	
	public boolean saveCrime() {
		
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "Crimes have been saved");
			return true;
			
		} catch (Exception e) {
			Log.d(TAG, "Crimes have not been saved");
			return false;
		}
		
	}
	
	public void delete(Crime c){
		mCrimes.remove(c);
	}
	

}
