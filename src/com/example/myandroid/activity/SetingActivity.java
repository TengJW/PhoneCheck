package com.example.myandroid.activity;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.util.NotificationUtil;
import com.example.myandroid.view.ActionView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SetingActivity extends BaseActivity {
	private ActionView actionview;
	private ToggleButton toggleButton;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seting);
		initActionBar("…Ë÷√", R.drawable.btn_homeasup_default, -1,
				OnClickListener);
		toggleButton = (ToggleButton) findViewById(R.id.ima_seting_2);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					NotificationUtil.startNotification(SetingActivity.this);
				} else {
					NotificationUtil.cancleNotification(SetingActivity.this);
				}
			}
		});
	}

	public void hitsettingitem(View v) {
		Bundle bundle = new Bundle();
		int id = v.getId();
		switch (id) {
		case R.id.rel_seting_3:
			SharedPreferences spf = getSharedPreferences("lead_config",
					Context.MODE_PRIVATE);
			Editor editor = spf.edit();
			editor.putBoolean("isFirstRun", true);
			editor.commit();
			Intent intent = new Intent(SetingActivity.this, FirstActivity.class);
			intent.putExtra("className", SetingActivity.this.getClass()
					.getSimpleName());
			startActivity(intent);
			break;
		case R.id.rel_seting_5:
			// Intent intent2 = new Intent(SetingActivity.this,
			// TwoLeftActivity.class);
			// intent2.putExtra("Aboutour", SetingActivity.this.getClass()
			// .getSimpleName());

			bundle.putString("Aboutour", SetingActivity.this.getClass()
					.getSimpleName());
			startActivity(AboutOusActivity.class, bundle);
			break;

		case R.id.rel_seting_6:
			// Intent intent3 = new Intent(SetingActivity.this,
			// HomeActivity.class);
			// intent3.putExtra("Aboutour", SetingActivity.this.getClass()
			// .getSimpleName());
			// startActivity(intent3);
			break;
		default:
			break;
		}
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

			default:
				break;
			}

		}
	};

}
