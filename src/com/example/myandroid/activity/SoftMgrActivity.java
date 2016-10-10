package com.example.myandroid.activity;

import java.text.DecimalFormat;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.MemoryManager;
import com.example.myandroid.view.PieChartView;
import com.example.myandroid.R.drawable;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SoftMgrActivity extends BaseActivity {
	private PieChartView pieChart;
	private ProgressBar pb_soft_phone;
	private ProgressBar pb_soft_sdkard;
	private TextView tv_soft_phone;
	private TextView tv_soft_sdkard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_mgr);
		initActionBar("软件管理", R.drawable.btn_homeasup_default, -1,
				clickListener);
		initView();
		initLoadData();
	}

	private void initLoadData() {
		// 手机自身的内存大小
		long phoneSelTotal = MemoryManager.getPhoneSelfSize();
		long phoneSelfUnuse = MemoryManager.getPhoneSelfFreeSize();
		long phoneselfuse = phoneSelTotal - phoneSelfUnuse;
		// 手机内置内存大小
		long phoneSelfSDCardtotal = MemoryManager.getPhoneSelfSDCardSize();
		long phoneselfsdcardunuese = MemoryManager.getPhoneSelfSDCardFreeSize();
		long phoneselfsdcarduse = phoneSelfSDCardtotal - phoneselfsdcardunuese;

		long phoneoutsdcardtotal = 0;
		long phoneoutsdcardunuse = 0;
		long phoneoutsdcarduse = 0;
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// 手机外置内存大小
			phoneoutsdcardtotal = MemoryManager
					.getPhoneOutSDCardSize(getApplicationContext());
			phoneoutsdcardunuse = MemoryManager
					.getPhoneOutSDCardFreeSize(getApplicationContext());
			phoneoutsdcarduse = phoneoutsdcardtotal - phoneoutsdcardunuse;
		}

		// 手机内存总大小
		long phonetotalMemory = phoneSelTotal + phoneSelfSDCardtotal
				+ phoneoutsdcardtotal;
		// 计算手机内存所占的比例
		float phoneSpaceF = (phoneSelTotal + phoneSelfSDCardtotal)
				/ phonetotalMemory;

		// SD卡所占的比例
		float sdcardSpaceF = phoneoutsdcardtotal / phonetotalMemory;

		DecimalFormat df = new DecimalFormat("#.00");
		phoneSpaceF = Float.parseFloat(df.format(phoneSpaceF));
		sdcardSpaceF = Float.parseFloat(df.format(sdcardSpaceF));
		pieChart.setProprotionWidthAnim(phoneSpaceF, sdcardSpaceF);

		long phoneTotalSpace = phoneSelTotal + phoneSelfSDCardtotal;// 手机内置的所有内存
		long phoneUnuseSpace = phoneSelfUnuse + phoneselfsdcardunuese;
		long phoneUseSpace = phoneTotalSpace - phoneUnuseSpace;

		tv_soft_phone.setText("可用：" + CommonUtil.getFileInfo(phoneUnuseSpace)
				+ "/" + CommonUtil.getFileInfo(phoneTotalSpace));
		tv_soft_sdkard.setText("可用："
				+ CommonUtil.getFileInfo(phoneoutsdcardunuse) + "/"
				+ CommonUtil.getFileInfo(phoneoutsdcardtotal));

		pb_soft_phone.setMax((int) (phoneTotalSpace / 1024));
		pb_soft_phone.setProgress((int) (phoneUseSpace / 1024));

		pb_soft_sdkard.setMax((int) (phoneoutsdcardtotal / 1024));
		pb_soft_sdkard.setProgress((int) (phoneoutsdcarduse / 1024));
	}

	private void initView() {
		pieChart = (PieChartView) findViewById(R.id.pieChart);
		pb_soft_phone = (ProgressBar) findViewById(R.id.pb_soft_phone);
		pb_soft_sdkard = (ProgressBar) findViewById(R.id.pb_soft_sdkard);
		tv_soft_phone = (TextView) findViewById(R.id.tv_soft_phone);
		tv_soft_sdkard = (TextView) findViewById(R.id.tv_soft_sdkard);
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

			default:
				break;
			}
		}
	};

	public void hitListiem(View view) {
		int viewid = view.getId();
		switch (viewid) {
		case R.id.soft_class_all1:
		case R.id.soft_class_sys3:
		case R.id.soft_class_user2:

			Bundle bundle = new Bundle();
			bundle.putInt("viewid", viewid);
			startActivity(SoftmgrShowActivity.class, bundle);
			break;
		}
	}

}
