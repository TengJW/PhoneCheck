package com.example.myandroid.activity;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.PhoneMgrAdapter;
import com.example.myandroid.bean.PhoneInfo;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.MemoryManager;
import com.example.myandroid.util.PhoneManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneCheckActivity extends BaseActivity {
	private ListView lv_battery;
	private PhoneMgrAdapter adapter;
	private ProgressBar pb_battery_list;
	private ProgressBar pb_battery_check;
	private int currentBattery;
	private int temperaturebattery;
	private TextView tv_battery_process;
	private BatteryReceiver receiver;
	private LinearLayout ll_battery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_check);
		initActionBar("手机检测", R.drawable.btn_homeasup_default, -1,
				clickListener);
		initMainUI();
		adapter = new PhoneMgrAdapter(this);
		lv_battery.setAdapter(adapter);

		new Thread() {
			public void run() {
				asyncLoadData();
			}
		}.start();
	}

	private void initMainUI() {
		pb_battery_check = (ProgressBar) findViewById(R.id.pb_battery_check);
		pb_battery_check.setOnClickListener(clickListener);
		lv_battery = (ListView) findViewById(R.id.lv_battery);
		pb_battery_list = (ProgressBar) findViewById(R.id.pb_battery_list);
		tv_battery_process = (TextView) findViewById(R.id.tv_battery_process);
		receiver = new BatteryReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);// 过滤电池的Action
		registerReceiver(receiver, filter); // 注册一个广播
	}

	private void asyncLoadData() {
		lv_battery.setVisibility(View.INVISIBLE);
		pb_battery_list.setVisibility(View.VISIBLE);
		String title;
		String content;
		Drawable icon;
		PhoneManager manager = PhoneManager.getInstance(this);
		title = "设备名称：" + manager.getPhoneName1();
		content = "系统版本：" + manager.getPhoneSystemVersion();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_version);
		final PhoneInfo info = new PhoneInfo(title, content, icon);
		title = "全部运行内存："
				+ CommonUtil
						.getFileInfo(MemoryManager.getPhoneTotalRamMemory());
		content = "可运行内存："
				+ CommonUtil.getFileInfo(MemoryManager
						.getFreeMemoryRam(PhoneCheckActivity.this));
		icon = getResources().getDrawable(R.drawable.setting_info_icon_space);
		final PhoneInfo info2 = new PhoneInfo(title, content, icon);

		title = "CPU名称：" + manager.getPhoneCpuName();
		content = "CPU数量：" + manager.getPhoneCpuNumber();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_cpu);
		final PhoneInfo info3 = new PhoneInfo(title, content, icon);

		title = "手机分辨率：" + manager.getResolution();
		content = "相机分辨率："; // manager.getMaxPhotoSize();
		icon = getResources().getDrawable(R.drawable.setting_info_icon_camera);
		final PhoneInfo info4 = new PhoneInfo(title, content, icon);

		title = "基带版本：" + manager.getPhoneSystemBasebandVersion();
		content = "是否Root：" + (manager.isRoot() ? "是" : "否");
		icon = getResources().getDrawable(R.drawable.setting_info_icon_root);
		final PhoneInfo info5 = new PhoneInfo(title, content, icon);
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				lv_battery.setVisibility(View.VISIBLE);
				pb_battery_list.setVisibility(View.INVISIBLE);
				adapter.addDataToAdapter(info);
				adapter.addDataToAdapter(info2);
				adapter.addDataToAdapter(info3);
				adapter.addDataToAdapter(info4);
				adapter.addDataToAdapter(info5);
				adapter.notifyDataSetChanged();

			}
		});
	}

	/**
	 * 四大组件中的第二个 广播通知
	 * 
	 * @author Administrator
	 * 
	 */
	public class BatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				Bundle bundle = intent.getExtras();
				int maxBattery = (Integer) bundle// 电池的总量
						.get(BatteryManager.EXTRA_SCALE);

				currentBattery = (Integer) bundle // 当前电量
						.get(BatteryManager.EXTRA_LEVEL);

				temperaturebattery = (Integer) bundle // 电池的温度
						.get(BatteryManager.EXTRA_TEMPERATURE) / 10;

				pb_battery_check.setMax(maxBattery);
				pb_battery_check.setProgress(currentBattery);
				int use100 = currentBattery * 100 / maxBattery;
				tv_battery_process.setText(use100 + "%");

			}
		}
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.pb_battery_check:
				showDeatial();
				break;
			default:
				break;
			}

		}

		private void showDeatial() {
			new AlertDialog.Builder(PhoneCheckActivity.this)
					.setTitle("电池详情")
					.setItems(
							new String[] { "当前电量 " + currentBattery,
									"手机温度 " + temperaturebattery +" °C"}, null).show();

		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	};

}
