package com.example.myandroid.activity;

import com.example.myandroid.MainActivity;
import com.example.myandroid.R;
import com.example.myandroid.flash;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.MyPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstActivity extends BaseActivity implements OnClickListener {
	private ViewPager viewpager;
	private TextView textView;
	private MyPagerAdapter myPagerAdapter;
	private ImageView[] images = new ImageView[3];
	private boolean ifFromClassName = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 检测配置信息，判断是否需要运行当前引导页面
		Intent in = getIntent();
		String className = in.getStringExtra("className");
		if (className != null && className.equals("SetingActivity")) {
			ifFromClassName = true;
		}
		SharedPreferences preferences = getSharedPreferences("lead_config",
				Context.MODE_PRIVATE);
		boolean isFirstRun = preferences.getBoolean("isFirstRun", true);// 默认为true
		if (!isFirstRun) {
			startActivity(MainActivity.class);
			finish();
		} // 从当前页面开始执行
		else {
			savePreferences();
			setContentView(R.layout.activity_first);
			initUI();
			init();
			initData();
		}

	}

	private void savePreferences() { // 执行该方法可以把key为isFirstRun的值改为false
		SharedPreferences preferences = getSharedPreferences("lead_config",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirstRun", false);
		editor.commit();
	}

	private void init() {
		myPagerAdapter = new MyPagerAdapter(this);
		viewpager.setAdapter(myPagerAdapter);
		textView.setOnClickListener(this);
		viewpager.setOnPageChangeListener(listener);
	}

	private void initUI() {
		images[0] = (ImageView) findViewById(R.id.imageviewtwofirst);
		images[1] = (ImageView) findViewById(R.id.imageviewtwofirst2);
		images[2] = (ImageView) findViewById(R.id.imageviewtwofirst3);
		viewpager = (ViewPager) findViewById(R.id.viewpagertwofirst);
		textView = (TextView) findViewById(R.id.textviewtwofirst);
		images[0].setBackgroundResource(R.drawable.lvdian);
		textView.setVisibility(View.INVISIBLE);
	}

	private OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			textView.setVisibility(View.INVISIBLE);
			if (arg0 >= 2) {
				textView.setVisibility(View.VISIBLE);
			}
			for (int i = 0; i < images.length; i++) {
				images[i].setBackgroundResource(R.drawable.huidian);
			}
			images[arg0].setBackgroundResource(R.drawable.lvdian);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	private void initData() {
		ImageView imageView = null;
		imageView = (ImageView) getLayoutInflater().inflate(
				R.layout.viewpage_item, null);
		imageView.setBackgroundResource(R.drawable.aaa);
		myPagerAdapter.addToAdpterView(imageView);
		imageView = (ImageView) getLayoutInflater().inflate(
				R.layout.viewpage_item, null);
		imageView.setBackgroundResource(R.drawable.bbb);
		myPagerAdapter.addToAdpterView(imageView);
		imageView = (ImageView) getLayoutInflater().inflate(
				R.layout.viewpage_item, null);
		imageView.setBackgroundResource(R.drawable.ccc);
		myPagerAdapter.addToAdpterView(imageView);
		myPagerAdapter.notifyDataSetChanged();// 是将Adapter进行一个刷新
	}

	@Override
	public void onClick(View v) {
		if (ifFromClassName) {
			startActivity(SetingActivity.class);
			finish();
		} else {
			startActivity(flash.class);
			finish();

		}
	}

}
