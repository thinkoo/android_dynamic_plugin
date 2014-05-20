/**
 * 
 */
package com.example.androidtest;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @author luoyuan.myp
 *
 * 2014-3-24
 */
public class MyWebChromeClient extends WebChromeClient {
	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
			JsPromptResult result) {
		// TODO Auto-generated method stub
		return true;
	}
}
