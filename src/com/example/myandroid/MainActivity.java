package com.example.myandroid;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.activity.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private Button btlogin;
	private EditText edt1;
	private EditText edt2;
	private CheckBox cb_main_remember_id;
	private CheckBox cb_main_remember_num;
	private boolean isSelect;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("��������onCreate");
		btlogin = (Button) findViewById(R.id.bt);
		cb_main_remember_id = (CheckBox) findViewById(R.id.cb_main_remember_id);
		cb_main_remember_num = (CheckBox) findViewById(R.id.cb_main_remember_num);

		cb_main_remember_id.setOnCheckedChangeListener(checkedChangeListener);
		cb_main_remember_num.setOnCheckedChangeListener(checkedListener);
		edt1 = (EditText) findViewById(R.id.edt1);
		edt2 = (EditText) findViewById(R.id.edt2);

		SharedPreferences preferences = getSharedPreferences("loginid",
				Context.MODE_PRIVATE);
		String isFirstRun = preferences.getString("id", null);// Ĭ��Ϊtrue

		SharedPreferences preferences2 = getSharedPreferences("loginnum",
				Context.MODE_PRIVATE);
		String isFirstRun2 = preferences2.getString("num", null);// Ĭ��Ϊtrue
		if (isFirstRun != null) {
			edt1.setText(isFirstRun);
			cb_main_remember_id.setChecked(true);
		}
		if (isFirstRun2 != null) {
			edt2.setText(isFirstRun2);
			cb_main_remember_num.setChecked(true);
		}

		btlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String num = edt1.getText().toString();
				String pwd = edt2.getText().toString();
				if (num.equals("123")) {
					if (pwd.equals("456")) {
						Intent it = new Intent();
						it = it.putExtra("content", num);
						startActivity(HomeActivity.class);
						finish();
					} else {
						Toast.makeText(MainActivity.this, "�������!!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "�˺Ų�����!!�˺���123��������456",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			String s = edt1.getText().toString();
			if (isChecked) {
				if (!s.equals("")) {
					saveidPreferences();
				} else {
					Toast.makeText(MainActivity.this, "�˺Ų���Ϊ��",
							Toast.LENGTH_SHORT).show();
					setChecked(cb_main_remember_id, false);
				}
			} else {
				delidPreferences();
			}
		}
	};

	private void saveidPreferences() { // ִ�и÷������԰�keyΪid��ֵ��Ϊ�����ֵ
		SharedPreferences preferences = getSharedPreferences("loginid",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("id", edt1.getText().toString());
		editor.commit();
	}

	private void delidPreferences() { // ִ�и÷������԰�keyΪid��ֵ��Ϊ�����ֵ
		SharedPreferences preferences = getSharedPreferences("loginid",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("id", null);
		editor.commit();
	}

	private OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			String s = edt2.getText().toString();
			if (isChecked) {
				if (!s.equals("")) {
					Toast.makeText(MainActivity.this, "��ס�����з���",
							Toast.LENGTH_SHORT).show();
					savenumPreferences();
				} else {
					Toast.makeText(MainActivity.this, "���벻��Ϊ��",
							Toast.LENGTH_SHORT).show();
					setChecked(cb_main_remember_num, false);

				}
			} else {
				delnumPreferences();
			}
		}
	};

	private void savenumPreferences() { // ִ�и÷������԰�keyΪid��ֵ��Ϊ�����ֵ
		SharedPreferences preferences = getSharedPreferences("loginnum",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("num", edt2.getText().toString());
		editor.commit();
	}

	private void delnumPreferences() { // ִ�и÷������԰�keyΪid��ֵ��Ϊ�����ֵ
		SharedPreferences preferences = getSharedPreferences("loginnum",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("num", null);
		editor.commit();
	}

	private void setChecked(CheckBox cb, boolean is) {
		cb.setChecked(is);
	}
}