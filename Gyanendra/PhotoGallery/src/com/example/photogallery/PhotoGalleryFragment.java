package com.example.photogallery;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

 public class PhotoGalleryFragment extends VisibleFragment {
	
	public static final String TAG ="PhotoGalleryFragment"; 
	
	private GridView mGridView;
	private ArrayList<GalleryItem> mItem;
	
	private ThumbNailDownloader<ImageView> mThumbnailDownloader;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		setHasOptionsMenu(true);
		updateItems();
		
		mThumbnailDownloader = new ThumbNailDownloader<ImageView>(new Handler());
		mThumbnailDownloader.setListner(new ThumbNailDownloader.Listner<ImageView>() {

			@Override
			public void onThumbnailDownloaded(ImageView token, Bitmap thumbnail) {
				if (isVisible()) {
					token.setImageBitmap(thumbnail);
					
				}
				
			}
		});
		mThumbnailDownloader.start();
		mThumbnailDownloader.getLooper();
		Log.i(TAG, "Background Thread has been started");
	}
	
	public void updateItems(){
		new FetcherItemTask().execute();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
		
		mGridView = (GridView) v.findViewById(R.id.gridView);
		
		setupAdapter();
		
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GalleryItem item = mItem.get(position);
				
				Uri photoPageUri = Uri.parse(item.getURL());
				
/*				Intent i = new Intent(Intent.ACTION_VIEW, photoPageUri);
				
				startActivity(i);
*/
				Intent i = new Intent(getActivity(), PhotoPageActivity.class);
				i.setData(photoPageUri);
				startActivity(i);
				
			}
		});
		
		
		
		
		return v;
	}
	
	private class FetcherItemTask extends AsyncTask<Void,Void,ArrayList<GalleryItem>>{

		@Override
		protected ArrayList<GalleryItem> doInBackground(Void... params) {
			
			if (getActivity()==null) {
				return new ArrayList<GalleryItem>();
				
			}
						
			String query = PreferenceManager.getDefaultSharedPreferences(getActivity())
					.getString(FlickerFetcher.PREF_SEARCH_QUERY, null);
			
			if(query !=null){
				return new FlickerFetcher().searchItems(query);
			} else {
				return new FlickerFetcher().fetchItems();
			}
			
			
			
		}

		@Override
		protected void onPostExecute(ArrayList<GalleryItem> result) {
			// TODO Auto-generated method stub
			mItem =result;
			setupAdapter();
			
		}
		
		

	}

public class GalleryAdapter extends ArrayAdapter<GalleryItem>{
		
		public GalleryAdapter(ArrayList<GalleryItem> items){
			super(getActivity(), 0, items);
			}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			if (convertView == null) {
				
				convertView = getActivity().getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
				
			}
			
			ImageView imageView = (ImageView)convertView.findViewById(R.id.galleryItemImageView);
			
			imageView.setImageResource(R.drawable.brian_up_close);
			
			GalleryItem item = getItem(position);
			mThumbnailDownloader.queueThumbnail(imageView, item.getmURL());
			
			return convertView;
			
		}
		
	}
		
	
	void setupAdapter(){
		if((getActivity()==null) || mGridView ==null)
			return;
		if(mItem != null) {
			mGridView.setAdapter(new GalleryAdapter(mItem));
		} else {
			mGridView.setAdapter(null);
		}
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mThumbnailDownloader.quit();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mThumbnailDownloader.clearQueues();
		
		}
	
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_photo_gallery, menu);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			MenuItem searchItem = menu.findItem(R.id.searchMenuItem);
			SearchView searchView =(SearchView) searchItem.getActionView();
			SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
			ComponentName component = getActivity().getComponentName();
			SearchableInfo searchInfo = searchManager.getSearchableInfo(component);
			searchView.setSearchableInfo(searchInfo);
			}
		
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.searchMenuItem:
			getActivity().onSearchRequested();
			return true;
		case R.id.clearMenuItem:	
			PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
			.putString(FlickerFetcher.PREF_SEARCH_QUERY, null).commit();
			updateItems();
			return true;
			
		case R.id.pollingMenuItem:
			boolean shouldAlarmStart = !PollService.isAlarmOn(getActivity());
			PollService.setAlarmService(getActivity(), shouldAlarmStart);
			if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB) {
				getActivity().invalidateOptionsMenu();
			}
			
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		
		MenuItem item = menu.findItem(R.id.pollingMenuItem);
		
		if(PollService.isAlarmOn(getActivity())){
			item.setTitle(R.string.stop_polling);
		} else {
			item.setTitle(R.string.start_polling);
		}
	}
	
}


