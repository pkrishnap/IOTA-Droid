package com.example.photogallery;

public class GalleryItem {
	
	private String mCaption;
	private String mId;
	private String mURL;
	private String mOwner;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mCaption;
	}

	public String getmCaption() {
		return mCaption;
	}

	public void setmCaption(String mCaption) {
		this.mCaption = mCaption;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmURL() {
		return mURL;
	}

	public void setmURL(String mURL) {
		this.mURL = mURL;
	}

	public String getmOwner() {
		return mOwner;
	}

	public void setmOwner(String mOwner) {
		this.mOwner = mOwner;
	}
	
	public String getURL(){
		return "https://www.flickr.com/photos/" + mOwner + "/" + mId;
		
	}
	
	

}
