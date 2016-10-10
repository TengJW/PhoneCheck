package com.example.myandroid.activity;

import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;
import com.example.myandroid.R.menu;
import com.example.myandroid.adapter.TelShowAdapter;
import com.example.myandroid.adapter.TelmgrGridAdapter;
import com.example.myandroid.bean.TableClass;
import com.example.myandroid.util.DBManager;
import com.example.myandroid.Base.BaseActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;

public class TelmgrShowActivity extends BaseActivity {
	private ListView lv_telshow;
	private TelShowAdapter adapter;
	private List<TableClass> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telmgr_show);
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		int idx = intent.getIntExtra("idx", 1);
		initActionBar(title, R.drawable.btn_homeasup_default, -1,
				OnClickListener);
		lv_telshow = (ListView) findViewById(R.id.lv_telshow);
		adapter = new TelShowAdapter(this);
		lv_telshow.setAdapter(adapter);
		lv_telshow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String number = adapter.getItem(position).getNumber() + "";
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ number));
				startActivity(intent);
			}
		});
		asyncTaskloadData(idx);
	}

	private void asyncTaskloadData(final int idx) {
		new Thread() {
			public void run() {
				list = DBManager.readTableClass("table" + idx);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						adapter.setDataTypeListToAdapter(list);
						adapter.notifyDataSetChanged();
					}
				});
			}
		}.start();

	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				startActivity(TelMgrActivity.class);
				finish();
				break;

			default:
				break;
			}

		}
	};

}
