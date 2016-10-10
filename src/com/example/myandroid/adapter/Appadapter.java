package com.example.myandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.example.myandroid.bean.AppInfo;

public class Appadapter extends BaseBaseAdapter<AppInfo> {
	private LayoutInflater layoutInflater;

	public Appadapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_softshow_item,
					null);
			vh.ivapp_icon_softshow = (ImageView) convertView
					.findViewById(R.id.ivapp_icon_softshow);
			vh.cb_app_softshow = (CheckBox) convertView
					.findViewById(R.id.cb_app_softshow);
			vh.tv_app_lable_softshow = (TextView) convertView
					.findViewById(R.id.tv_app_lable_softshow);
			vh.tv_app_packageName_softshow = (TextView) convertView
					.findViewById(R.id.tv_app_packageName_softshow);
			vh.tv_app_version_softshow = (TextView) convertView
					.findViewById(R.id.tv_app_version_softshow);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String title = getItem(position).getPackageInfo().applicationInfo
				.loadLabel(context.getPackageManager()).toString();
		String packageName = getItem(position).getPackageInfo().packageName;
		String version = getItem(position).getPackageInfo().versionName;
		boolean isdel = getItem(position).isDel();
		Bitmap bitmap = ((BitmapDrawable) getItem(position).getPackageInfo().applicationInfo
				.loadIcon(context.getPackageManager())).getBitmap();
		vh.tv_app_lable_softshow.setText(title);
		vh.tv_app_packageName_softshow.setText(packageName);
		vh.tv_app_version_softshow.setText(version);
		vh.ivapp_icon_softshow.setImageBitmap(bitmap);
		// vh.cb_app_softshow.setChecked(isdel);
		vh.cb_app_softshow.setTag(position);
		vh.cb_app_softshow.setOnCheckedChangeListener(changeListener);
		vh.cb_app_softshow.setChecked(getItem(position).isDel());
		return convertView;
	}

	class ViewHolder {
		ImageView ivapp_icon_softshow;
		CheckBox cb_app_softshow;
		TextView tv_app_lable_softshow, tv_app_packageName_softshow,
				tv_app_version_softshow;
	}

	private OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = (Integer) buttonView.getTag();
			getDataList().get(position).setDel(isChecked);
		}
	};

}
