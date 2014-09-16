package com.example.photogallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class VisibleFragment extends Fragment {
	
	public static final String TAG = "VisibleFragment";
	
	
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "Got A Broadcast", Toast.LENGTH_LONG).show();
			setResultCode(Activity.RESULT_CANCELED);
			
			
			
		}
	};


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
		getActivity().unregisterReceiver(mBroadcastReceiver);
		
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
		getActivity().registerReceiver(mBroadcastReceiver, filter,PollService.PERM_PRIVATE,null);
	}
	
	

}
