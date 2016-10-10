package com.example.myandroid.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.myandroid.R;
import com.example.myandroid.Base.BaseActivity;
import com.example.myandroid.adapter.FileAdapter;
import com.example.myandroid.bean.FileInfo;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.FileManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FilemgrShowActivity extends BaseActivity {
	private int id;
	private TextView tv_file_num, tv_file_size;
	private ListView lv_file_list;
	private Button btn_file_clean;
	private long fileSize;
	private long fileNum;
	private List<FileInfo> fileinfo;
	private String title;
	private FileAdapter adapter;
	private CheckBox cb_app_file_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemgr_show);
		id = getIntent().getIntExtra("id", R.id.tv_file_all_size);
		initInfo(id);
		initActionBar(title, R.drawable.btn_homeasup_default, -1, clickListener);
		initUI();
		adapter = new FileAdapter(this);
		lv_file_list.setAdapter(adapter);
		adapter.setDataTypeListToAdapter(fileinfo);
		adapter.notifyDataSetChanged();
	}

	private void initUI() {
		tv_file_num = (TextView) findViewById(R.id.tv_file_num);
		tv_file_size = (TextView) findViewById(R.id.tv_file_size);
		lv_file_list = (ListView) findViewById(R.id.lv_file_list);
		btn_file_clean = (Button) findViewById(R.id.btn_file_clean);
		tv_file_num.setText(fileNum + "个");
		tv_file_size.setText(CommonUtil.getFileInfo(fileSize));
		btn_file_clean.setOnClickListener(clickListener);
		// cb_app_file_show = (CheckBox) findViewById(R.id.cb_app_file_show);
		// cb_app_file_show.setOnCheckedChangeListener(changeListener);

	}

	private void initInfo(int id2) {
		switch (id) {
		case R.id.rel_file_1:
			fileinfo = FileManager.getFileManager().getAnyFileList();
			fileSize = FileManager.getFileManager().getAnyFileSize();
			title = "全部文件";
			break;
		case R.id.rel_file_2:
			fileinfo = FileManager.getFileManager().getTxtFileList();
			fileSize = FileManager.getFileManager().getTxtFileSize();
			title = "文本文件";
			break;
		case R.id.rel_file_3:
			fileinfo = FileManager.getFileManager().getVideoFileList();
			fileSize = FileManager.getFileManager().getVideoFileSize();
			title = "视频";
			break;
		case R.id.rel_file_4:
			fileinfo = FileManager.getFileManager().getAudioFileList();
			fileSize = FileManager.getFileManager().getAudioFileSize();
			title = "音频";
			break;
		case R.id.rel_file_5:
			fileinfo = FileManager.getFileManager().getImageFileList();
			fileSize = FileManager.getFileManager().getImageFileSize();
			title = "图片";
			break;
		case R.id.rel_file_6:
			fileinfo = FileManager.getFileManager().getZipFileList();
			fileSize = FileManager.getFileManager().getZipFileSize();
			title = "压缩包";
			break;
		case R.id.rel_file_7:
			fileinfo = FileManager.getFileManager().getApkFileList();
			fileSize = FileManager.getFileManager().getApkFileSize();
			title = "安装包";
			break;
		}
		fileNum = fileinfo.size();
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_file_clean:
				deleteFile();
				Toast.makeText(FilemgrShowActivity.this, "aaa",
						Toast.LENGTH_SHORT).show();
				break;

			case R.id.avimage1:
				setResult(1);
				finish();
				break;
			}

		}

		private void deleteFile() {
			List<FileInfo> deleFileInfos = new ArrayList<FileInfo>();// 用来保存删除的文件
			for (int i = 0; i < adapter.getDataList().size(); i++) {
				FileInfo info = adapter.getDataList().get(i);
				if (info.isSelect()) {
					deleFileInfos.add(info);
				}
			}
			for (int j = 0; j < deleFileInfos.size(); j++) {
				FileInfo deFileInfo = deleFileInfos.get(j);
				File file = deFileInfo.getFile();
				long size = file.length();
				if (file.delete()) {
					adapter.getDataList().remove(deFileInfo);
					FileManager.getFileManager().getAnyFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setAnyFileSize(
							FileManager.getFileManager().getAnyFileSize()
									- size);
					FileManager.getFileManager().getTxtFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setTxtFileSize(
							FileManager.getFileManager().getTxtFileSize()
									- size);

					FileManager.getFileManager().getVideoFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setVideoFileSize(
							FileManager.getFileManager().getVideoFileSize()
									- size);

					FileManager.getFileManager().getAudioFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setAudioFileSize(
							FileManager.getFileManager().getAudioFileSize()
									- size);

					FileManager.getFileManager().getImageFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setImageFileSize(
							FileManager.getFileManager().getImageFileSize()
									- size);

					FileManager.getFileManager().getZipFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setZipFileSize(
							FileManager.getFileManager().getZipFileSize()
									- size);

					FileManager.getFileManager().getApkFileList()
							.remove(deFileInfo);

					FileManager.getFileManager().setApkFileSize(
							FileManager.getFileManager().getApkFileSize()
									- size);

					switch (id) {
					case R.id.rel_file_1:
						fileSize = FileManager.getFileManager()
								.getAnyFileSize();
						break;
					case R.id.rel_file_2:

						fileSize = FileManager.getFileManager()
								.getTxtFileSize();
						break;
					case R.id.rel_file_3:

						fileSize = FileManager.getFileManager()
								.getVideoFileSize();
						break;
					case R.id.rel_file_4:

						fileSize = FileManager.getFileManager()
								.getAudioFileSize();
						break;
					case R.id.rel_file_5:

						fileSize = FileManager.getFileManager()
								.getImageFileSize();
						break;
					case R.id.rel_file_6:

						fileSize = FileManager.getFileManager()
								.getZipFileSize();
						break;
					case R.id.rel_file_7:

						fileSize = FileManager.getFileManager()
								.getApkFileSize();
						break;
					}
				}
			}
			adapter.notifyDataSetChanged();
			fileNum = adapter.getDataList().size();
			tv_file_num.setText(fileNum + "个");
			tv_file_size.setText(CommonUtil.getFileInfo(fileSize));

		}
	};

	/**
	 * 全选的checkbox
	 */
	//
	// private OnCheckedChangeListener changeListener = new
	// OnCheckedChangeListener() {
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView,
	// boolean isChecked) {
	// List<FileInfo> listdate = adapter.getDataList();
	// for (FileInfo run : listdate) {
	// run.setSelect(isChecked);
	// }
	// adapter.notifyDataSetChanged();
	// }
	// };

}
