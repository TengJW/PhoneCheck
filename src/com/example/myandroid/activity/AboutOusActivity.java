package com.example.myandroid.activity;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.view.ActionView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutOusActivity extends BaseActivity {
	private ActionView actionView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_left);
		initActionBar("关于我们", R.drawable.btn_homeasup_default, -1,
				OnClickListener);
	}

	private OnClickListener OnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				Intent in = getIntent();
				String fromclass = in.getStringExtra("Aboutour");
				if (fromclass != null && fromclass.equals("SetingActivity")) {
					startActivity(SetingActivity.class);
					finish();
				} else {
					startActivity(SetingActivity.class);
					finish();
				}
				break;
			default:
				break;
			}

		}
	};

}
