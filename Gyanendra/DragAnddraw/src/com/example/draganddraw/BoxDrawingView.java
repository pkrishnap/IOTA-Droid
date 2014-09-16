package com.example.draganddraw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoxDrawingView extends View {
	
	public static final String TAG = "BoxDrawingView";
	private Box mCurrentBox;
	private ArrayList<Box> mBoxes = new ArrayList<Box>();
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;

	public BoxDrawingView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public BoxDrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mBoxPaint = new Paint();
		mBoxPaint.setColor(0x22ff0000);
		
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		PointF curr = new PointF(event.getX(), event.getY());
		
		Log.i(TAG, "Received input at X: " + curr.x + " Y: " + curr.y);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "Action Down");
			mCurrentBox = new Box(curr);
			mBoxes.add(mCurrentBox);
			break;
		
		case MotionEvent.ACTION_UP:
			Log.i(TAG, "Action Up");
			mCurrentBox = null;
			break;

		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "Action move");
			if(mCurrentBox != null) {
				mCurrentBox.setmCurrent(curr);
				invalidate();
			}
				
			break;
		
		case MotionEvent.ACTION_CANCEL:
			Log.i(TAG, "Action Cancel");
			mCurrentBox =null;
			break;
			
		default:
			break;
		}
		
		
		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawPaint(mBackgroundPaint);
		
		for (Box box : mBoxes) {
			
			float left = Math.min(box.getmOrigin().x, box.getmCurrent().x);
			float right = Math.max(box.getmOrigin().x, box.getmCurrent().x);
			float top = Math.min(box.getmOrigin().y, box.getmCurrent().y);
			float bottom = Math.max(box.getmOrigin().y, box.getmCurrent().y);
			
			canvas.drawRect(left, top, right, bottom, mBoxPaint);	
			
		}
	}


}
