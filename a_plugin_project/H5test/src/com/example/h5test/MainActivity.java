package com.example.h5test;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

	WebView wv ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initV();
		loadUrl();
	}
	private void loadUrl() {
		String url = "";
		wv.loadUrl(url);
	}
	private void initV() {
		wv = (WebView) findViewById(R.id.webView1);
		WebSettings set= wv.getSettings();
		set.setJavaScriptEnabled(true);
	}


}
