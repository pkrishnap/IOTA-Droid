package com.example.nerdlauncher;

import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NerdLauncherFragment extends ListFragment {
	
	public static final String TAG ="Nerdlauncherfragment";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent startUpIntent = new Intent(Intent.ACTION_MAIN);
		startUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(startUpIntent, 0);
		
		Log.d(TAG, "I have found " + activities.size() + " activities");
		
		
		ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(), android.R.layout.simple_list_item_1, activities){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View v = super.getView(position, convertView, parent);
				
				PackageManager pm = getActivity().getPackageManager();
				
				TextView tv = (TextView) v;
				ResolveInfo ri = getItem(position);
				tv.setText(ri.loadLabel(pm));
				return v;
			}
			
			
		};
		
		setListAdapter(adapter);
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		ResolveInfo resolveInfo = (ResolveInfo) getListAdapter().getItem(position);
		ActivityInfo activityinfo = resolveInfo.activityInfo;
		
		if (activityinfo == null) {
			return;
			
		}
		
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setClassName(activityinfo.applicationInfo.packageName, activityinfo.name);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		
		
		
	}
	
	
	

}
