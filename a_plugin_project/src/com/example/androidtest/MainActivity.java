package com.example.androidtest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	Button btn;
	WebView webView1;
	public String wap;
	public String file;
	public String urltest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.my_view_layout);
		LinearLayout layout = new LinearLayout(mProxyActivity);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		layout.setBackgroundColor(Color.parseColor("#F79AB5"));
		Button button = new Button(mProxyActivity);
		button.setText("button");
		layout.addView(button, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mProxyActivity, "you clicked button", Toast.LENGTH_SHORT).show();
				startActivityByProxy("com.example.androidtest.MyFragmentActivity");
			}
		});

		mProxyActivity.setContentView(layout);
		// btn = (Button) findViewById(R.id.opDB);
		// slientInstall(new File(""));
		// btn.setBackgroundResource(0);
		// doDB();
		// CheckNetworkState();
		// checkNet();
		// beep();
		// vibrator();

		// doWebview();
		// zhengze();
	}

	class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (!TextUtils.isEmpty(url) && !url.startsWith("http") && !url.startsWith("https")
					&& !url.startsWith("ftp")) {
				try {
					Uri uri = Uri.parse(url);
					Intent it = new Intent();
					it.setData(uri);
					startActivity(it);
				} catch (Exception e) {
				}

				return true;
			}
			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	private void doWebview() {
		webView1 = (WebView) findViewById(R.id.webView1);
		webView1.addJavascriptInterface(new JsO(), "js_b");
		webView1.setWebChromeClient(new MyWebChromeClient());
		webView1.setWebViewClient(new MyWebViewClient());

		WebSettings webSettings = webView1.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAppCacheEnabled(true); // 默认是关闭的
		webSettings.setAppCacheMaxSize(1024 * 1024 * 5); // 缓存大小
		webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setDomStorageEnabled(true);
		// 不让保存用户密码，保存的话会明文存放在/data/app/中，root的用户可以很容易拿到用户数据
		webSettings.setSavePassword(false);
		webSettings.setDatabaseEnabled(true);
		// 使webview支持localstorage必须设置此属性
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);

		urltest = "http://10.62.52.11:8000/demo/demo.html";
		wap = "http://b.ued.taobao.net/liuhuo.gk/smartbanner/demo/demo.html";
		file = "file:///android_asset/jscall.html";

		webView1.loadUrl(wap);
		btn = (Button) findViewById(R.id.opDB);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (webView1.canGoBack()) {
					webView1.goBack();

				} else {
					finish();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// if (keyCode == KeyEvent.KEYCODE_BACK) {
		// if (webView1.canGoBack()) {
		// webView1.goBack();
		// return true;
		// }
		// }
		return super.onKeyDown(keyCode, event);
	}

	class JsO {
		public void test() {
			Toast.makeText(getApplicationContext(), "test in", 0).show();
			Log.e("xxx", "asdfasdfasfasdf");
		}
	}

	/**
	 * 静默安装
	 * 
	 * @param file
	 * @return
	 */
	public boolean slientInstall(File file) {
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
			dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
					+ file.getPath());
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			int value = process.waitFor();

			// 代表成功
			if (value == 0) {
				result = true;
			} else if (value == 1) { // 失败
				result = false;
			} else { // 未知情况
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result;
	}

	private void doDB() {
		// TODO Auto-generated method stub
		Button btnDB = (Button) findViewById(R.id.opDB);
		btnDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File dbfile = new File("/mnt/sdcard/trip.db");
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
				db.beginTransaction();
				Cursor cursorOldALL = db.rawQuery("select * from train_station", null);
				Log.e("==", " cursorOldALL.getCount() = " + cursorOldALL.getCount());

				File txtfile = new File("/mnt/sdcard/traincity.txt");
				BufferedReader r = null;
				try {
					r = new BufferedReader(new InputStreamReader(new FileInputStream(txtfile),
							"UTF-8"));
					String line = null;
					String city = "";
					String pinyin = "";
					String jiancheng = "";
					int lines = 0;
					while ((line = r.readLine()) != null) {
						String[] arr = line.split("#");
						city = arr[0];
						pinyin = arr[1];
						jiancheng = arr[2];
						String sqlstr = "insert into train_station values ('" + city + "','"
								+ pinyin + "','" + jiancheng + "');";
						db.execSQL(sqlstr);
						lines++;
					}
					db.setTransactionSuccessful();
					Log.e("==", " lines = " + lines);

					Cursor cursorALL = db.rawQuery("select * from train_station", null);
					Log.e("==", " cursorALL.getCount() = " + cursorALL.getCount());

					Cursor cursoritem = db
							.rawQuery(
									"select * from train_station where station_name='艾不盖' and station_jianpin='abg';",
									null);
					Log.e("==", " cursor.getCount()= " + cursoritem.getCount());
					Log.e("==", " cursor.getString(0)= " + cursoritem.getString(0));

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					db.endTransaction();
					try {
						r.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					db.close();
				}

			}
		});
	}

	private void checkNet() {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfoActivity = connectivityManager.getActiveNetworkInfo();
		if (mobNetInfoActivity != null) {
			int netFlag = mobNetInfoActivity.getType();
			switch (netFlag) {
			case 0:
				// gsm
				break;
			case 1:
				// wifi
				break;
			default:
				break;
			}
			Log.e("===", "netFlag = " + netFlag);
		} else {
			Log.e("===", "no net info ");
		}
	}

	public void CheckNetworkState() {
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			Toast.makeText(getApplicationContext(), "mobile = " + mobile, 1).show();
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			Toast.makeText(getApplicationContext(), "wifi = " + wifi, 1).show();
		showTips();
	}

	private void showTips() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("netstate");
		builder.setMessage("setnetwork");
		builder.setPositiveButton("okok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 如果没有网络连接，则进入网络设置界面
				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}

	private void zhengze() {
		// TODO Auto-generated method stub
		// 查找以Java开头,任意结尾的字符串
		Pattern pattern = Pattern.compile("^Java.*");
		Matcher matcher = pattern.matcher("Java不是人");
		boolean b = matcher.matches();
		// 当条件满足时，将返回true，否则返回false
		System.out.println(b);
	}

	private void vibrator() {
		// TODO Auto-generated method stub
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
	}

	private void beep() {
		// TODO Auto-generated method stub
		ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM,
				ToneGenerator.MAX_VOLUME);
		toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wap));
		startActivity(viewIntent);
		return super.onOptionsItemSelected(item);
	}

}
