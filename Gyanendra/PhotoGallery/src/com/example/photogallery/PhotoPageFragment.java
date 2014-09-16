package com.example.photogallery;

import android.annotation.SuppressLint;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhotoPageFragment extends VisibleFragment {
	
	String mUrl;
	private WebView mWebView;
	
	ProgressBar mProgressBar;
	TextView mProgressTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mUrl = getActivity().getIntent().getData().toString();
		
		
		
	}

	@SuppressLint("SetJavaScriptEnabled") @Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_photo_page, container, false);
		
		mWebView = (WebView) v.findViewById(R.id.webView);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		mProgressBar.setMax(100);
		mProgressTextView = (TextView) v.findViewById(R.id.progressTextView);
		
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
		
		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if(newProgress == 100){
					mProgressBar.setVisibility(View.INVISIBLE);
					
				} else {
					mProgressBar.setVisibility(View.VISIBLE);
					mProgressBar.setProgress(newProgress);
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				mProgressTextView.setText(title);
			}
			
		});
		
		mWebView.loadUrl(mUrl);
		
		return v;
	}
	
	

}
