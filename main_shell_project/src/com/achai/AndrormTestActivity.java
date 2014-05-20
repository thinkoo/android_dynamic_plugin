package com.achai;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.orm.androrm.DatabaseAdapter;
import com.orm.androrm.Model;

public class AndrormTestActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AndrormTestActivity.this, ProxyActivity.class);
				intent.putExtra(ProxyActivity.EXTRA_DEX_PATH,
						"/mnt/sdcard/testplugin/plugin.apk");
				startActivity(intent);
			}
		});
		// long before = System.currentTimeMillis();
		// Log.d("ormtest", "creating " + getClass() + " at " + before);
		// syncDb();
		// for (int i = 0; i < 10; i++) {
		// Name name = new Name();
		// name.mName.set("achai" + i);
		// name.save(this);
		// }
		//
		// long after = System.currentTimeMillis();
		// Log.d("ormtest", "spend time " + getClass() + " at " + (after -
		// before));
		// TextView tv = new TextView(this);
		// tv.setText("result :" + (after - before));
		// setContentView(tv);
	}

	void syncDb() {
		List<Class<? extends Model>> models = new ArrayList<Class<? extends Model>>();
		models.add(Name.class);
		DatabaseAdapter.setDatabaseName("andrormtest.db");
		DatabaseAdapter adapter = new DatabaseAdapter(getApplicationContext());
		adapter.setModels(models);
	}
}