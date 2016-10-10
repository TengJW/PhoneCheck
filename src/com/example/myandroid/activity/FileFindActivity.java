package com.example.myandroid.activity;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.FileManager;
import com.example.myandroid.util.FileTypeUtil;
import com.example.myandroid.util.FileManager.SearchFileListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FileFindActivity extends BaseActivity {
	private FileManager fileManager;
	private Thread thread;

	private ProgressBar file_find_pro1;
	private ProgressBar file_find_pro2;
	private ProgressBar file_find_pro3;
	private ProgressBar file_find_pro4;
	private ProgressBar file_find_pro5;
	private ProgressBar file_find_pro6;
	private ProgressBar file_find_pro7;
	private ImageView file_find_img1;
	private ImageView file_find_img2;
	private ImageView file_find_img3;
	private ImageView file_find_img4;
	private ImageView file_find_img5;
	private ImageView file_find_img6;
	private ImageView file_find_img7;
	private TextView tv_file_all_size;
	private TextView tv_file_text_size;
	private TextView tv_file_video_size;
	private TextView tv_file_music_size;
	private TextView tv_file_image_size;
	private TextView tv_file_var_size;
	private TextView tv_file_apk_size;
	private TextView tv_file_find;
	private RelativeLayout rel_file_1;
	private RelativeLayout rel_file_2;
	private RelativeLayout rel_file_3;
	private RelativeLayout rel_file_4;
	private RelativeLayout rel_file_5;
	private RelativeLayout rel_file_6;
	private RelativeLayout rel_file_7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_find);
		initActionBar("文件管理", R.drawable.btn_homeasup_default, -1,
				clickListener);
		findid();
		loadData();
		asyncTaskLoadData();
	}

	private void asyncTaskLoadData() {
		fileManager = FileManager.getFileManager();
		fileManager.setSearchFileListener(onclFileListener);
		thread = new Thread() {
			public void run() {
				fileManager.searchSDCardFile();
			};
		};
		thread.start();

	}

	@Override
	protected void myHandlerMessage(Message msg) {
		switch (msg.what) {
		case 1:
			String typeName = (String) msg.obj;
			tv_file_find.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_file_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			if (typeName.equals(FileTypeUtil.TYPE_APK)) {
				tv_file_apk_size.setText(CommonUtil.getFileInfo(fileManager
						.getApkFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_AUDIO)) {
				tv_file_music_size.setText(CommonUtil.getFileInfo(fileManager
						.getAudioFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_IMAGE)) {
				tv_file_image_size.setText(CommonUtil.getFileInfo(fileManager
						.getImageFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_TXT)) {
				tv_file_text_size.setText(CommonUtil.getFileInfo(fileManager
						.getTxtFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_VIDEO)) {
				tv_file_video_size.setText(CommonUtil.getFileInfo(fileManager
						.getVideoFileSize()));
			} else if (typeName.equals(FileTypeUtil.TYPE_ZIP)) {
				tv_file_var_size.setText(CommonUtil.getFileInfo(fileManager
						.getZipFileSize()));
			}

			break;

		case 2:
			tv_file_find.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_file_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_file_apk_size.setText(CommonUtil.getFileInfo(fileManager
					.getApkFileSize()));
			tv_file_music_size.setText(CommonUtil.getFileInfo(fileManager
					.getAudioFileSize()));
			tv_file_image_size.setText(CommonUtil.getFileInfo(fileManager
					.getImageFileSize()));
			tv_file_text_size.setText(CommonUtil.getFileInfo(fileManager
					.getTxtFileSize()));
			tv_file_video_size.setText(CommonUtil.getFileInfo(fileManager
					.getVideoFileSize()));
			tv_file_var_size.setText(CommonUtil.getFileInfo(fileManager
					.getZipFileSize()));

			file_find_pro1.setVisibility(View.INVISIBLE);
			file_find_pro2.setVisibility(View.INVISIBLE);
			file_find_pro3.setVisibility(View.INVISIBLE);
			file_find_pro4.setVisibility(View.INVISIBLE);
			file_find_pro5.setVisibility(View.INVISIBLE);
			file_find_pro6.setVisibility(View.INVISIBLE);
			file_find_pro7.setVisibility(View.INVISIBLE);

			file_find_img1.setVisibility(View.VISIBLE);
			file_find_img2.setVisibility(View.VISIBLE);
			file_find_img3.setVisibility(View.VISIBLE);
			file_find_img4.setVisibility(View.VISIBLE);
			file_find_img5.setVisibility(View.VISIBLE);
			file_find_img6.setVisibility(View.VISIBLE);
			file_find_img7.setVisibility(View.VISIBLE);

			break;
		}
	}

	private void findid() {

		file_find_pro1 = (ProgressBar) findViewById(R.id.file_find_pro1);
		file_find_pro2 = (ProgressBar) findViewById(R.id.file_find_pro2);
		file_find_pro3 = (ProgressBar) findViewById(R.id.file_find_pro3);
		file_find_pro4 = (ProgressBar) findViewById(R.id.file_find_pro4);
		file_find_pro5 = (ProgressBar) findViewById(R.id.file_find_pro5);
		file_find_pro6 = (ProgressBar) findViewById(R.id.file_find_pro6);
		file_find_pro7 = (ProgressBar) findViewById(R.id.file_find_pro7);

		file_find_img1 = (ImageView) findViewById(R.id.file_find_img1);
		file_find_img2 = (ImageView) findViewById(R.id.file_find_img2);
		file_find_img3 = (ImageView) findViewById(R.id.file_find_img3);
		file_find_img4 = (ImageView) findViewById(R.id.file_find_img4);
		file_find_img5 = (ImageView) findViewById(R.id.file_find_img5);
		file_find_img6 = (ImageView) findViewById(R.id.file_find_img6);
		file_find_img7 = (ImageView) findViewById(R.id.file_find_img7);

		tv_file_all_size = (TextView) findViewById(R.id.tv_file_all_size);
		tv_file_text_size = (TextView) findViewById(R.id.tv_file_text_size);
		tv_file_video_size = (TextView) findViewById(R.id.tv_file_video_size);
		tv_file_music_size = (TextView) findViewById(R.id.tv_file_music_size);
		tv_file_image_size = (TextView) findViewById(R.id.tv_file_image_size);
		tv_file_var_size = (TextView) findViewById(R.id.tv_file_var_size);
		tv_file_apk_size = (TextView) findViewById(R.id.tv_file_apk_size);

		tv_file_find = (TextView) findViewById(R.id.tv_file_find);// 所有文件的总大小

		rel_file_1 = (RelativeLayout) findViewById(R.id.rel_file_1);
		rel_file_2 = (RelativeLayout) findViewById(R.id.rel_file_2);
		rel_file_3 = (RelativeLayout) findViewById(R.id.rel_file_3);
		rel_file_4 = (RelativeLayout) findViewById(R.id.rel_file_4);
		rel_file_5 = (RelativeLayout) findViewById(R.id.rel_file_5);
		rel_file_6 = (RelativeLayout) findViewById(R.id.rel_file_6);
		rel_file_7 = (RelativeLayout) findViewById(R.id.rel_file_7);

		rel_file_1.setOnClickListener(clickListener);
		rel_file_2.setOnClickListener(clickListener);
		rel_file_3.setOnClickListener(clickListener);
		rel_file_4.setOnClickListener(clickListener);
		rel_file_5.setOnClickListener(clickListener);
		rel_file_6.setOnClickListener(clickListener);
		rel_file_7.setOnClickListener(clickListener);

	}

	private FileManager.SearchFileListener onclFileListener = new SearchFileListener() {

		@Override
		public void searching(String typeName) {
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = typeName;
			handler.sendMessage(msg);
		}

		@Override
		public void end(boolean isExceptionEnd) {
			handler.sendEmptyMessage(2);
		}
	};

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.avimage1:
				// startActivity(HomeActivity.class);
				finish();
				break;
			case R.id.rel_file_1:
			case R.id.rel_file_2:
			case R.id.rel_file_3:
			case R.id.rel_file_4:
			case R.id.rel_file_5:
			case R.id.rel_file_6:
			case R.id.rel_file_7:
				Intent intent = new Intent(FileFindActivity.this,
						FilemgrShowActivity.class);
				intent.putExtra("id", id);
				startActivityForResult(intent, 1);
				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {

			tv_file_find.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_file_all_size.setText(CommonUtil.getFileInfo(fileManager
					.getAnyFileSize()));
			tv_file_apk_size.setText(CommonUtil.getFileInfo(fileManager
					.getApkFileSize()));
			tv_file_music_size.setText(CommonUtil.getFileInfo(fileManager
					.getAudioFileSize()));
			tv_file_image_size.setText(CommonUtil.getFileInfo(fileManager
					.getImageFileSize()));
			tv_file_text_size.setText(CommonUtil.getFileInfo(fileManager
					.getTxtFileSize()));
			tv_file_video_size.setText(CommonUtil.getFileInfo(fileManager
					.getVideoFileSize()));
			tv_file_var_size.setText(CommonUtil.getFileInfo(fileManager
					.getZipFileSize()));

		}
	};

	private void loadData() {
		file_find_pro1.setVisibility(View.VISIBLE);
		file_find_pro2.setVisibility(View.VISIBLE);
		file_find_pro3.setVisibility(View.VISIBLE);
		file_find_pro4.setVisibility(View.VISIBLE);
		file_find_pro5.setVisibility(View.VISIBLE);
		file_find_pro6.setVisibility(View.VISIBLE);
		file_find_pro7.setVisibility(View.VISIBLE);
		file_find_img1.setVisibility(View.INVISIBLE);
		file_find_img2.setVisibility(View.INVISIBLE);
		file_find_img3.setVisibility(View.INVISIBLE);
		file_find_img4.setVisibility(View.INVISIBLE);
		file_find_img5.setVisibility(View.INVISIBLE);
		file_find_img6.setVisibility(View.INVISIBLE);
		file_find_img7.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		fileManager.setStopSearch(true);
	}

}
