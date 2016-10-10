package com.example.myandroid.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.R.drawable;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {
	private Context context;
	private List<HashMap<String, Object>> list;

	int[] images = new int[] { R.drawable.ic_launcher};

	public MyBaseAdapter(Context context, List<HashMap<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh ;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.threeitem, null);
			vh = new ViewHolder();
			vh.tv_title = (TextView) convertView.findViewById(R.id.ItemTitle);
			vh.ImageView = (ImageView) convertView.findViewById(R.id.Itemimage);
			vh.tv_content = (TextView) convertView
					.findViewById(R.id.ItemContent);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		 vh.ImageView.setImageResource(images[0]);
		 vh.tv_title.setText(list.get(position).get("ItemTitle").toString());
		 vh.tv_content.setText(list.get(position).get("ItemContent").toString());
		return convertView;
	}

	class ViewHolder {
		private TextView tv_title, tv_content;
		private ImageView ImageView;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
