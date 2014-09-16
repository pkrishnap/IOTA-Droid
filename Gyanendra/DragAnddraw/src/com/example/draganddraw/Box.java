package com.example.draganddraw;

import android.graphics.PointF;

public class Box {
	
	private PointF mOrigin;
	private PointF mCurrent;
	
	
	public Box(PointF origin) {
		mOrigin = mCurrent=origin;
	}


	public PointF getmCurrent() {
		return mCurrent;
	}


	public void setmCurrent(PointF mCurrent) {
		this.mCurrent = mCurrent;
	}


	public PointF getmOrigin() {
		return mOrigin;
	}
	
	
	

}
