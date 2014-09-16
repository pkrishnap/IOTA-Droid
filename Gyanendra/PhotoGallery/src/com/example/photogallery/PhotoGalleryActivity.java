package com.example.photogallery;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class PhotoGalleryActivity extends SingleFragmentActivity {
	
	public static final String TAG ="PhotoGalleryActivity";

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new PhotoGalleryFragment();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		PhotoGalleryFragment fragment = (PhotoGalleryFragment) 
				getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			String query = intent.getStringExtra(SearchManager.QUERY);
			PreferenceManager.getDefaultSharedPreferences(this)
			.edit().putString(FlickerFetcher.PREF_SEARCH_QUERY, query)
			.commit();
		}
		
		fragment.updateItems();
	
	}

    
}
