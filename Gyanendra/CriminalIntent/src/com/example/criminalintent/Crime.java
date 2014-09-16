package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	
	private UUID mID;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private String mSuspect;
	
	private static final String JSON_ID ="id";
	private static final String JSON_TITLE ="title";
	private static final String JSON_SOLVED ="solved";
	private static final String JSON_DATE ="date";
	private static final String JSON_SUSPECT ="suspect";
	
	
	public Crime(){
		mID = UUID.randomUUID();
		mDate = new Date();
	}
	
	
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public UUID getmID() {
		return mID;
	}


	public Date getmDate() {
		return mDate;
	}


	public String getmSuspect() {
		return mSuspect;
	}


	public void setmSuspect(String mSuspect) {
		this.mSuspect = mSuspect;
	}


	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}


	public boolean ismSolved() {
		return mSolved;
	}


	public void setmSolved(boolean mSolved) {
		this.mSolved = mSolved;
	}
	
	@Override
	public String toString(){
		return mTitle;
	}
	
	public JSONObject toJSON() throws JSONException{
		
		JSONObject json = new JSONObject();
		json.put(JSON_ID,mID.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_SOLVED, mSolved);
		json.put(JSON_DATE, mDate.getTime());
		json.put(JSON_SUSPECT, mSuspect);
		
		return json;
		
	}
	
	public Crime(JSONObject json) throws JSONException {
		mID = UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLE)){
			mTitle =json.getString(JSON_TITLE);
			
		}
		
		mSolved = json.getBoolean(JSON_SOLVED);
		mDate = new Date(json.getLong(JSON_DATE));
		
		if (json.has(JSON_SUSPECT)) {
			mSuspect = json.getString(JSON_SUSPECT);
			
		}
		
	}
	
	

}
