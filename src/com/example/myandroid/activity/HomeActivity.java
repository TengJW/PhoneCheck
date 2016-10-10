package com.example.myandroid.activity;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.util.AppInfoManager;
import com.example.myandroid.util.MemoryManager;
import com.example.myandroid.view.ActionView;
import com.example.myandroid.view.ClearArcView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {
	private ActionView actionview;
	private ClearArcView clearArcView;
	private TextView textView;
	private ImageView imageView;
	private TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paint);
		initActionBar("功能", -1, R.drawable.ic_child_configs, OnClickListener);
		initView();
		initMemoryData();
	}

	private void initView() {
		clearArcView = (ClearArcView) findViewById(R.id.clearView);
		textView = (TextView) findViewById(R.id.tv_score);
		imageView = (ImageView) findViewById(R.id.iv_home_score);
		textView2 = (TextView) findViewById(R.id.tv_speed);
		textView2.setOnClickListener(OnClickListener);
		imageView.setOnClickListener(OnClickListener);

	}

	private void initMemoryData() {
		float totalMemory = MemoryManager.getPhoneTotalRamMemory();// 总的系统内存
		float freeMemory = MemoryManager.getFreeMemoryRam(HomeActivity.this);// 可用内存大小
		float userMemory = totalMemory - freeMemory;
		float usep = userMemory / totalMemory;
		int user100 = (int) (usep * 100);
		textView.setText(user100 + "");
		int useAngle = (int) (usep * 360);
		clearArcView.setAngleWidthAnim(useAngle);
	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.tv_speed:
			case R.id.iv_home_score:
				AppInfoManager.getAppInfoManager(HomeActivity.this)
						.KillAllProcesser();
				initMemoryData();
				break;

			case R.id.avimage2:
				startActivity(SetingActivity.class);
			}
		}
	};

	public void hitClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.tv_paint_jiasu:
			startActivity(SmashActivity.class);
			break;
		case R.id.tv_paint_guanli:
			startActivity(SoftMgrActivity.class);
			break;
		case R.id.tv_paint_jiance:
			startActivity(PhoneCheckActivity.class);
			break;
		case R.id.tv_paint_tongxun:
			startActivity(TelMgrActivity.class);
			break;
		case R.id.tv_paint_file:
			startActivity(FileFindActivity.class);
			break;
		case R.id.tv_paint_clean:
			startActivity(CleanActivity.class);
			break;

		default:
			break;
		}
	}

}
