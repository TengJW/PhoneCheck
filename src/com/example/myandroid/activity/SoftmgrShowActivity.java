package com.example.myandroid.activity;

import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.Appadapter;
import com.example.myandroid.adapter.RunningAdapter;
import com.example.myandroid.bean.AppInfo;
import com.example.myandroid.bean.RunningAppInfo;
import com.example.myandroid.util.AppInfoManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SoftmgrShowActivity extends BaseActivity {
	private int id;
	private String title;
	private ListView lv_soft_show;
	private ProgressBar pb_clear_soft;
	private CheckBox ck_soft_show;
	private Button btn_show_progress_soft;
	private Appadapter adapter;
	private AppInfoReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_softmgr_show);
		int idview = getIntent().getIntExtra("viewid", R.id.soft_class_sys3);
		switch (idview) {
		case R.id.soft_class_all1:
			this.title = "全部软件";
			break;
		case R.id.soft_class_sys3:
			this.title = "系统软件";
			break;
		case R.id.soft_class_user2:
			this.title = "用户软件";
			break;
		}
		initActionBar(title, R.drawable.btn_homeasup_default, -1,
				OnClickListener);
		this.id = idview;
		initView();
		adapter = new Appadapter(this);
		lv_soft_show.setAdapter(adapter);
		initLoadData();
	}

	private List<AppInfo> listinfo = null;

	private void initLoadData() {
		pb_clear_soft.setVisibility(View.VISIBLE);
		lv_soft_show.setVisibility(View.INVISIBLE);
		new Thread() {
			public void run() {
				switch (id) {
				case R.id.soft_class_all1:
					listinfo = AppInfoManager.getAppInfoManager(
							SoftmgrShowActivity.this).getAllPackageInfos(true);
					break;
				case R.id.soft_class_sys3:
					listinfo = AppInfoManager.getAppInfoManager(
							SoftmgrShowActivity.this).getsysPackageInfos(true);
					break;
				case R.id.soft_class_user2:
					listinfo = AppInfoManager.getAppInfoManager(
							SoftmgrShowActivity.this).getusePackageInfos(true);
					break;
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						adapter.setDataTypeListToAdapter(listinfo);
						adapter.notifyDataSetChanged();
						pb_clear_soft.setVisibility(View.INVISIBLE);
						lv_soft_show.setVisibility(View.VISIBLE);
					}
				});
			}
		}.start();
	}

	private void initView() {
		lv_soft_show = (ListView) findViewById(R.id.lv_soft_show);
		pb_clear_soft = (ProgressBar) findViewById(R.id.pb_clear_soft);
		ck_soft_show = (CheckBox) findViewById(R.id.ck_soft_show);
		btn_show_progress_soft = (Button) findViewById(R.id.btn_show_progress_soft);
		btn_show_progress_soft.setOnClickListener(OnClickListener);
		ck_soft_show.setOnCheckedChangeListener(changeListener);
		receiver = new AppInfoReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addAction(AppInfoReceiver.APP_DEL);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);

	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				startActivity(SoftMgrActivity.class);
				finish();
				break;
			case R.id.btn_show_progress_soft:
				List<AppInfo> listinfos = adapter.getDataList();
				for (AppInfo ai : listinfos) {
					String packagename = ai.getPackageInfo().packageName;
					if (ai.isDel()) {
						Intent inte = new Intent();
						inte.setAction(Intent.ACTION_DELETE);
						inte.setData(Uri.parse("package:" + packagename));
						startActivity(inte);
					}
				}
				break;
			}
		}
	};

	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			List<AppInfo> listdate = adapter.getDataList();
			for (AppInfo run : listdate) {
				// run.setClear(isChecked);
				run.setDel(isChecked);
			}
			adapter.notifyDataSetChanged();
		}
	};

	public class AppInfoReceiver extends BroadcastReceiver {
		public static final String APP_DEL = "com.example.myandroid";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_PACKAGE_REMOVED)
					|| action.equals(APP_DEL)) {
				initLoadData();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}
