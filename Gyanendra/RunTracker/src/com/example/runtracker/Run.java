package com.example.runtracker;

import java.util.Date;

public class Run {
	
	private Date mStartDate;
	private long mID;
	
	public Run(){
		mStartDate = new Date();
	}

	public Date getmStartDate() {
		return mStartDate;
	}

	public void setmStartDate(Date mStartDate) {
		this.mStartDate = mStartDate;
	}
	
	public long getmID() {
		return mID;
	}

	public void setmID(long mID) {
		this.mID = mID;
	}

	public int getDurationSecond(long endMillies) {
		
		return (int) ((endMillies -mStartDate.getTime())/1000);
		
	}
	
	public static String formatDuration(int durationSeconds){
		int seconds = durationSeconds % 60;
		int minutes = ((durationSeconds -seconds)/60)%60;
		int hours = (durationSeconds -seconds - (minutes*60))/3600;
		
		return String.format("%2d:%2d:%2d", hours,minutes,seconds);
	}
	

}
