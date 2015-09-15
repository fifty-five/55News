package com.fiftyfive.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;


public class WebViewActivity extends Activity{

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        WebView myWebView;
        String URL="https://ecommerce.55labs.com";

		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		myWebView = new WebView(this);
		setContentView(myWebView);

		//DataLayer dataLayer = TagManager.getInstance(this).getDataLayer();
		//dataLayer.push(DataLayer.mapOf("event", "applicationStart"));

		WebSettings webSettings = myWebView.getSettings();
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);

		myWebView.setWebViewClient(new MyWebViewClient());
		myWebView.loadUrl(URL);
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			 	if (Uri.parse(url).getHost().equals("www.fifty-five.fr")) {
		            // This is my web site, so do not override; let my WebView load the page
		            return false;
		        }
			 
			// Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}
	}

	public void onPause()
	{
		super.onPause();
	}

	public void onResume()
	{
		super.onResume();
	}

}
