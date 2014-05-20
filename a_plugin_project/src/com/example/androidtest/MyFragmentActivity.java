package com.example.androidtest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;


/**   
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author luoyuan.myp 洛远 
 * @date 2013-11-13 下午9:11:40 
 * @version V1.0   
 */
public class MyFragmentActivity extends FragmentActivity{
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		TextView tv = new TextView(getApplicationContext());
		tv.setText("act 2");
		setContentView(tv);
		
	}
	
	
}
