package com.example.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;


public class AudioPlayer extends MediaPlayer {
	MediaPlayer mPlayer;
	
	public void stop(){
		if(mPlayer != null)	{
			
			mPlayer.release();
			mPlayer = null;
			
		}
	}
	
	public void start(Context c){
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		mPlayer.start();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
				
			}
		});

	}

}
