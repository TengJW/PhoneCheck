package com.example.myandroid.activity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;
import com.example.myandroid.R.menu;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.ClearMgrAdapter;
import com.example.myandroid.bean.ClearBean;
import com.example.myandroid.bean.RunningAppInfo;
import com.example.myandroid.util.CleanManager;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.FileManager;

import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CleanActivity extends BaseActivity implements OnClickListener{
	private ListView lv_clear;
	private ClearMgrAdapter adapter;
	private ProgressBar pb_clear;
	private long total_size = 0;
	private TextView tv_clear_file;
	private CheckBox ck_clear;
	private ImageView avimage1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean);
		initActionBar("¿¨ª¯«Â¿Ì", R.drawable.btn_homeasup_default, -1, null);
		ck_clear = (CheckBox) findViewById(R.id.ck_clear);
		ck_clear.setOnCheckedChangeListener(linstener);
		avimage1 = (ImageView) findViewById(R.id.avimage1);
		avimage1.setOnClickListener(this);

		initMainUI();
		adapter = new ClearMgrAdapter(this);
		lv_clear.setAdapter(adapter);

		try {
			asyncTaskLoadData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					CleanManager.readclearDB(getResources().getAssets().open(
							"clearpath.db"));
					List<ClearBean> list = CleanManager.readClassListTable();
					System.out.println(list);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	private void initMainUI() {
		lv_clear = (ListView) findViewById(R.id.lv_clear);
		pb_clear = (ProgressBar) findViewById(R.id.pb_clear);
		tv_clear_file = (TextView) findViewById(R.id.tv_clear_file);

	}

	@Override
	protected void myHandlerMessage(Message msg) {
		switch (msg.what) {
		case 1:
			long size = (Long) msg.obj;
			total_size += size;
			tv_clear_file.setText(CommonUtil.getFileInfo(total_size));

			break;
		case 2:
			List<ClearBean> listviewdata = (List<ClearBean>) msg.obj;
			adapter.setDataTypeListToAdapter(listviewdata);
			adapter.notifyDataSetChanged();
			pb_clear.setVisibility(View.INVISIBLE);
			lv_clear.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	private void asyncTaskLoadData() throws IOException {
		total_size = 0;
		pb_clear.setVisibility(View.VISIBLE);
		lv_clear.setVisibility(View.INVISIBLE);
		CleanManager.readclearDB(getResources().getAssets()
				.open("clearpath.db"));
		final List<ClearBean> list = CleanManager
				.getPhoneSoftDetial(CleanActivity.this);
		new Thread() {
			public void run() {
				for (ClearBean cb : list) {
					File file = new File(cb.getFilepath());
					long size = FileManager.getFileSize(file);
					cb.setSize(size);
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = size;
					handler.sendMessage(msg);
				}
				Message msg = handler.obtainMessage();
				msg.what = 2;
				msg.obj = list;
				handler.sendMessage(msg);

			};
		}.start();
	}

	private OnCheckedChangeListener linstener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			List<ClearBean> listdate = adapter.getDataList();
			for (ClearBean run : listdate) {
				run.setChecked(arg1);
			}
			adapter.notifyDataSetChanged();

		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.avimage1:
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
