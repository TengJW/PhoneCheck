package com.example.myandroid.activity;

import java.util.List;
import java.util.Map;
import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.RunningAdapter;
import com.example.myandroid.bean.RunningAppInfo;
import com.example.myandroid.util.AppInfoManager;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.MemoryManager;
import com.example.myandroid.util.SystemManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SmashActivity extends BaseActivity {
	private TextView tv_speedup_show;
	private TextView tv_phoneModel;
	private ProgressBar pb_progress;
	private TextView phone_ram;
	private ListView speedup_listview;
	private ProgressBar pb_progerss_list;
	private Button btn_show_process;
	private RunningAdapter adapter;
	private Button btn_oneKeySpeed_show;
	private CheckBox ck_speedup;
	private Map<Integer, List<RunningAppInfo>> appInfos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash);
		initActionBar("手机加速", R.drawable.btn_homeasup_default, -1,
				OnClickListener);
		speedup_listview = (ListView) findViewById(R.id.speedup_listview);
		pb_progerss_list = (ProgressBar) findViewById(R.id.pb_progress_list);
		btn_oneKeySpeed_show = (Button) findViewById(R.id.btn_oneKeySpeed_show);
		ck_speedup = (CheckBox) findViewById(R.id.ck_speedup);
		btn_oneKeySpeed_show.setOnClickListener(OnClickListener);
		ck_speedup.setOnCheckedChangeListener(changeListener);
		adapter = new RunningAdapter(this);
		speedup_listview.setAdapter(adapter);
		initView();
		loadData();
	}

	private void initView() {
		tv_speedup_show = (TextView) findViewById(R.id.tv_speedup_show);
		tv_phoneModel = (TextView) findViewById(R.id.tv_phoneModel);
		pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
		phone_ram = (TextView) findViewById(R.id.phone_ram);
		btn_show_process = (Button) findViewById(R.id.btn_oneKeySpeed);
		btn_show_process.setOnClickListener(OnClickListener);
		initphoneDate();
		initRamData();
	}

	private void initRamData() {
		float totalMemory = MemoryManager.getPhoneTotalRamMemory();// 总的系统内存
		float freeMemory = MemoryManager.getFreeMemoryRam(SmashActivity.this);// 可用内存大小
		float userMemory = totalMemory - freeMemory;
		float usep = userMemory / totalMemory;
		int user100 = (int) (usep * 100);
		pb_progress.setProgress(user100);
		phone_ram.setText("可用内存" + CommonUtil.getFileInfo((long) (freeMemory))
				+ "/" + CommonUtil.getFileInfo((long) (totalMemory)));
	}

	/**
	 * 设置手机信息
	 */

	private void initphoneDate() {

		tv_speedup_show.setText(SystemManager.getPhoneName());
		tv_phoneModel.setText(SystemManager.getPhoneModel());
	}

	private void loadData() {
		speedup_listview.setVisibility(View.INVISIBLE);
		pb_progerss_list.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				appInfos = AppInfoManager.getAppInfoManager(SmashActivity.this)
						.getRuningAppInfos();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						speedup_listview.setVisibility(View.VISIBLE);
						pb_progerss_list.setVisibility(View.INVISIBLE);
						initphoneDate();
						adapter.setDataTypeListToAdapter(appInfos
								.get(AppInfoManager.USER_TYPE_FLAG));
						adapter.setState(RunningAdapter.USER_FLAG);
						adapter.notifyDataSetChanged();
					}
				});
			};
		}.start();

	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.btn_oneKeySpeed:
				if (appInfos != null) {
					switch (adapter.getState()) {
					case RunningAdapter.USER_FLAG:
						adapter.setDataTypeListToAdapter(appInfos
								.get(AppInfoManager.SYSTEM_TYPE_FLAG));
						adapter.setState(RunningAdapter.SYSTEM_FLAG);
						btn_show_process.setText("显示应用程序");
						break;
					case RunningAdapter.SYSTEM_FLAG:
						adapter.setDataTypeListToAdapter(appInfos
								.get(AppInfoManager.USER_TYPE_FLAG));
						adapter.setState(RunningAdapter.USER_FLAG);
						btn_show_process.setText("显示系统程序");
						break;
					}
					adapter.notifyDataSetChanged();
				}
				break;
			case R.id.btn_oneKeySpeed_show:
				List<RunningAppInfo> listalldata = adapter.getDataList();
				for (RunningAppInfo run : listalldata) {
					if (run.isClear()) {
						String packageName = run.getPackageName();
						AppInfoManager.getAppInfoManager(SmashActivity.this)
								.KillProcesser(packageName);
					}
				}
				loadData();
				ck_speedup.setChecked(false);
				break;

			}
		}
	};

	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			List<RunningAppInfo> listdate = adapter.getDataList();
			for (RunningAppInfo run : listdate) {
				run.setClear(isChecked);
			}
			adapter.notifyDataSetChanged();
		}
	};

}
