package com.example.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

	@Override
	protected int getLayoutID() {
		// TODO Auto-generated method stub
		return R.layout.activity_masterdetail;
	}

	@Override
	public void onCrimeSelected(Crime crime) {
		// TODO Auto-generated method stub
		
		if (findViewById(R.id.detailFragmentContainer) == null) {
			
			Intent i = new Intent(this, CrimePagerActivity.class);
			i.putExtra(CrimeListFragment.EXTRA_MESSAGE, crime.getmID());
			startActivity(i);
		} else {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
			
			Fragment newDetails = CrimeFragment.newInstance(crime.getmID());
			
			if (oldDetail != null) {
				
				ft.remove(oldDetail);
				
			}
			
			ft.add(R.id.detailFragmentContainer, newDetails);
			ft.commit();
		}
		
	}
	
	
	

}
