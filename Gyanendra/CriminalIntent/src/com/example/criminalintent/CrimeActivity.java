package com.example.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        UUID crimeID = (UUID)(getIntent().getSerializableExtra(CrimeListFragment.EXTRA_MESSAGE));
        
        FragmentManager fm = getSupportFragmentManager();
        
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        
        if (fragment == null) {
        	
        	fragment = CrimeFragment.newInstance(crimeID);
        	fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
			
		}
        
    }
}
