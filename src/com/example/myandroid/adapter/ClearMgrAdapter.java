package com.example.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myandroid.R;
import com.example.myandroid.Base.BaseBaseAdapter;
import com.example.myandroid.adapter.PhoneMgrAdapter.ViewHolder;
import com.example.myandroid.bean.ClearBean;
import com.example.myandroid.util.CommonUtil;

public class ClearMgrAdapter extends BaseBaseAdapter<ClearBean> {
	LayoutInflater layoutInflater;

	public ClearMgrAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_clear_item,
					null);
			vh.iv_clear_icon_item = (ImageView) convertView
					.findViewById(R.id.iv_clear_icon_item);
			vh.tv_app_lable_clear = (TextView) convertView
					.findViewById(R.id.tv_app_lable_clear);
			vh.tv_app_system_clear = (TextView) convertView
					.findViewById(R.id.tv_app_system_clear);
			vh.cb_app_clear_item = (CheckBox) convertView
					.findViewById(R.id.cb_app_clear_item);
			vh.cb_app_clear_item = (CheckBox) convertView
					.findViewById(R.id.cb_app_clear_item);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.iv_clear_icon_item.setImageDrawable(getItem(position).getIcon());
		vh.tv_app_lable_clear.setText(getItem(position).getSoftChinesename());
		vh.tv_app_system_clear.setText(CommonUtil.getFileInfo(getItem(position)
				.getSize()));
		vh.cb_app_clear_item.setChecked(getItem(position).isChecked());

		return convertView;
	}

	class ViewHolder {
		ImageView iv_clear_icon_item;
		CheckBox cb_app_clear_item;
		TextView tv_app_lable_clear, tv_app_system_clear;
	}

}
