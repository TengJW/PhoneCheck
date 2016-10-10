package com.example.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myandroid.R;
import com.example.myandroid.Base.BaseBaseAdapter;
import com.example.myandroid.bean.TableClass;

public class TelShowAdapter extends BaseBaseAdapter<TableClass> {
	private LayoutInflater layoutInflater;

	public TelShowAdapter(Context context) {
		super(context);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.layout_tel_listitem,
					null);
			vh = new ViewHolder();
			vh.tv_tel_name = (TextView) convertView
					.findViewById(R.id.tv_tel_name);
			vh.tv_tel_num = (TextView) convertView
					.findViewById(R.id.tv_tel_num);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv_tel_name.setText(getItem(position).getName());
		vh.tv_tel_num.setText(getItem(position).getNumber() + "");
		return convertView;
	}

	class ViewHolder {
		TextView tv_tel_name, tv_tel_num;
	}

}
