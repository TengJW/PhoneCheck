package com.example.myandroid.activity;

import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;
import com.example.myandroid.R.menu;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.TelmgrGridAdapter;
import com.example.myandroid.bean.ClassInfo;
import com.example.myandroid.util.DBManager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class TelMgrActivity extends BaseActivity {
	private GridView gv_telmgr_view;
	private TelmgrGridAdapter adapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_mgr);
		initActionBar("通讯大全", R.drawable.btn_homeasup_default, -1, OnClickListener);
		gv_telmgr_view = (GridView) findViewById(R.id.gv_telmgr_view);
		adapter = new TelmgrGridAdapter(this);
		gv_telmgr_view.setAdapter(adapter);
		gv_telmgr_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int idx = adapter.getDataList().get(arg2).getIdx();
				String name = adapter.getDataList().get(arg2).getName();
				Bundle bundle = new Bundle();
				bundle.putString("title", name);
				bundle.putInt("idx", idx);
				startActivity(TelmgrShowActivity.class, bundle);
			}
		});
		asyncTaskLoadData();

		// new Thread() {
		// public void run() {
		// try {
		// DBManager.readUpdateDB(getResources().getAssets().open(
		// "commonnum.db"));
		// final List<ClassInfo> list = DBManager.readClassListTable();
		// runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// adapter.setDataTypeListToAdapter(list);
		// adapter.notifyDataSetChanged();
		// }
		// });
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// };
		//
		// }.start();
	}

	private void asyncTaskLoadData() {
		new Thread() {
			public void run() {
				try {
					DBManager.readUpdateDB(getResources().getAssets().open(
							"commonnum.db"));
					final List<ClassInfo> list = DBManager.readClassListTable();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							adapter.setDataTypeListToAdapter(list);
							adapter.notifyDataSetChanged();
						}
					});
				} catch (Exception e) {
				}
			}
		}.start();
	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				startActivity(SetingActivity.class);
				finish();
				break;

			default:
				break;
			}

		}
	};
}
