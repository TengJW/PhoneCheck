package com.example.myandroid.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseBaseAdapter;
import com.example.myandroid.bean.FileInfo;
import com.example.myandroid.util.CommonUtil;
import com.example.myandroid.util.FileTypeUtil;

public class FileAdapter extends BaseBaseAdapter<FileInfo> {
	private LayoutInflater layoutInflater;

	public FileAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_filemgr_item,
					null);
			vh.ivapp_icon_fileshow = (ImageView) convertView
					.findViewById(R.id.ivapp_icon_fileshow);
			vh.cb_app_file_show = (CheckBox) convertView
					.findViewById(R.id.cb_app_file_show);
			vh.tv_app_lable_fileshow = (TextView) convertView
					.findViewById(R.id.tv_app_lable_fileshow);
			vh.tv_app_packageName_fileshow = (TextView) convertView
					.findViewById(R.id.tv_app_packageName_fileshow);
			vh.tv_app_version_fileshow = (TextView) convertView
					.findViewById(R.id.tv_app_version_fileshow);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		FileInfo fileInfo = getItem(position);
		String name = fileInfo.getFile().getName();//
		String size = CommonUtil.getFileInfo(fileInfo.getFile().length());
		String lastTime = CommonUtil.getStrTime(fileInfo.getFile()
				.lastModified());
		boolean isCheck = fileInfo.isSelect();
		vh.tv_app_lable_fileshow.setText(name);
		vh.tv_app_version_fileshow.setText(size);
		vh.tv_app_packageName_fileshow.setText(lastTime);
		vh.cb_app_file_show.setChecked(isCheck);
		Bitmap bitmap = getBitMap(fileInfo);
		vh.ivapp_icon_fileshow.setImageBitmap(bitmap);
		vh.cb_app_file_show.setTag(position);
		vh.cb_app_file_show.setOnCheckedChangeListener(changeListener);
		vh.cb_app_file_show.setChecked(getItem(position).isSelect());
		return convertView;
	}

	public Bitmap getBitMap(FileInfo fileInfo) {
		Bitmap bitmap = null;
		if (fileInfo.getFileType().equals(FileTypeUtil.TYPE_IMAGE)) {
			// bitmapfactory。decodefile可以将一个图片路径转换成bitmap对象
			bitmap = BitmapFactory.decodeFile(fileInfo.getFile().getPath());
			return bitmap;
		}
		Resources resources = context.getResources();
		int icon = resources.getIdentifier(fileInfo.getIcon(), "drawable",
				context.getPackageName());
		if (icon <= 0) {
			icon = R.drawable.icon_file;
		}
		bitmap = BitmapFactory.decodeResource(context.getResources(), icon);// 生成一个bitmap对象
		return bitmap;
	}

	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = (Integer) buttonView.getTag();
			getDataList().get(position).setSelect(isChecked);
		}
	};

	class ViewHolder {
		ImageView ivapp_icon_fileshow;
		CheckBox cb_app_file_show;
		TextView tv_app_lable_fileshow, tv_app_packageName_fileshow,
				tv_app_version_fileshow;
	}

}
