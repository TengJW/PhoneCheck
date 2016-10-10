package com.example.myandroid.adapter;

import android.content.Context;
import android.renderscript.Element.DataType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myandroid.R;
import com.example.myandroid.Base.BaseBaseAdapter;
import com.example.myandroid.bean.ClassInfo;

public class TelmgrGridAdapter extends BaseBaseAdapter<ClassInfo> {
	LayoutInflater layoutInflater = null;

	public TelmgrGridAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = layoutInflater
					.inflate(R.layout.layout_mrg_item, null);
			vh.tv_mrg_txt = (TextView) convertView
					.findViewById(R.id.tv_mrg_txt);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_mrg_txt.setText(getItem(position).getName());
		switch (position % 3) {
		case 0:
			convertView.setBackgroundResource(R.drawable.mgr_green);
			break;
		case 1:
			convertView.setBackgroundResource(R.drawable.mgr_red);
			break;
		case 2:
		default:
			convertView.setBackgroundResource(R.drawable.mgr_yellow);
			break;
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_mrg_txt;
	}

}
