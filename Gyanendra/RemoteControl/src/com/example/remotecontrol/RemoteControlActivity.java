package com.example.remotecontrol;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class RemoteControlActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        
    }

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new RemoteControlFragment();
	}


   
}
