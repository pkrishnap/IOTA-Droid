package com.example.hellomoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HelloMoonFragment extends Fragment {
	
	private Button mPlayButton;
	private Button mPauseButton;
	private AudioPlayer mAudioPlayer = new AudioPlayer(); 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_hello_moon, container, false);
		
		
		mPlayButton = (Button)v.findViewById(R.id.playButton);
		mPauseButton = (Button)v.findViewById(R.id.pauseButton);
		mPlayButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAudioPlayer.start(getActivity());
				
			}
		});
		
		
		mPauseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAudioPlayer.stop();
				
			}
		});
		
		
		return v;
		
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mAudioPlayer.stop();
		
	}
	
	
	

}
