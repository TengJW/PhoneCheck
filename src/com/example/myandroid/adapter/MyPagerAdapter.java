package com.example.myandroid.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {
	private Context context;
	private ArrayList<View> arrayList = new ArrayList<View>();

	public MyPagerAdapter(Context context) {
		super();
		this.context = context;
	}

	public void addToAdpterView(View view) {
		arrayList.add(view);
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(arrayList.get(position));

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(arrayList.get(position));
		return arrayList.get(position);
	}

}
