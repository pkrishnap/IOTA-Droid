package com.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	
	public static final String TAG ="CrimeListFragment";
	public static final String EXTRA_MESSAGE = "CrimeListFragment_Extra_Message";
	
	public ArrayList<Crime> mCrimes;
	private Callbacks mCallbacks;
	
	public interface Callbacks {
		void onCrimeSelected(Crime crime);
	}
	
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mCallbacks =null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.crime_title);
		mCrimes = CrimeLabs.get(getActivity()).getCrimes();
//		ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = (Crime) getListAdapter().getItem(position);
		Log.d(TAG, c.getmTitle()+" was Clicked");
		
		//Intent i = new Intent(getActivity(), CrimeActivity.class);
		//Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		//i.putExtra(EXTRA_MESSAGE, c.getmID());
		//startActivity(i);
		mCallbacks.onCrimeSelected(c);
		
	}
	

	
	
	private class CrimeAdapter extends ArrayAdapter<Crime>{
		
		public CrimeAdapter(ArrayList<Crime> crimes){
			super(getActivity(), 0, crimes);			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_crime, null);
			}
			
			Crime c = getItem(position);
			
			TextView mTitleTextView = (TextView)convertView.findViewById(R.id.crimeListItemTitleTextView);
			mTitleTextView.setText(c.getmTitle());
			
			TextView mDateTextView = (TextView)convertView.findViewById(R.id.crimListItemDateTextView);
			mDateTextView.setText(c.getmDate().toString());
			
			CheckBox mSolvedCheckBox = (CheckBox)convertView.findViewById(R.id.crimeListItemCheckbox);
					mSolvedCheckBox.setChecked(c.ismSolved());
					
			return convertView;		
			
			
			
			
		}
		
		
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
		
		
			}

	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
	}

	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		switch (item.getItemId()) {
		case R.id.newCrimeMenuItem:
			Crime crime = new Crime();
			CrimeLabs.get(getActivity()).addCrime(crime);
			//Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			//i.putExtra(EXTRA_MESSAGE, crime.getmID());
			//startActivityForResult(i, 0);
			((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
			mCallbacks.onCrimeSelected(crime);
			return true;
			
		case R.id.showSubTitlemenuItem:
			
			if(getActivity().getActionBar().getSubtitle() == null){
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
			} else	{
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getActivity().getMenuInflater().inflate(R.menu.menu_context_crime_list, menu);
		
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
		
			registerForContextMenu(listView);
				
		} else {
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.menu_context_crime_list, menu);
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// TODO Auto-generated method stub
					switch (item.getItemId()) {
					case R.id.deleteCrimeContextMenu:
						
						CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
						CrimeLabs crimeLabs = CrimeLabs.get(getActivity());
						
						for (int i = (adapter.getCount() -1); i >= 0; i--) {
							
							if(getListView().isItemChecked(i)){
								crimeLabs.delete(adapter.getItem(i));
							}
							
							
						}
						mode.finish();
						adapter.setNotifyOnChange(true);
						return true;
			

					default:
						return false;
					}
					
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					
					
				}
			});
		}
		

		return v;	
		
	}
	
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		int position = info.position;
		CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
		Crime c = adapter.getItem(position);
		
		switch (item.getItemId()){
		
		case(R.id.deleteCrimeContextMenu):
		{
			CrimeLabs.get(getActivity()).delete(c);
			adapter.notifyDataSetChanged();
			return true;
		}
			
		default:
			return super.onContextItemSelected(item);
		
		}
		
		
	}
	
	
	
	
	

	
	

}
