package com.example.myandroid.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myandroid.R;
import com.example.myandroid.R.drawable;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;
import com.example.myandroid.adapter.MyBaseAdapter;
import com.example.myandroid.view.ActionView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ThreeActivity extends Activity {
	private GridView gridView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);
		gridView = (GridView) findViewById(R.id.gridview);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("Itemimage", R.drawable.ic_launcher);
			hashMap.put("ItemTitle", "第" + i + "行");
			hashMap.put("ItemContent", "这是第" + i + "行");
			list.add(hashMap);
		}
		MyBaseAdapter adapter = new MyBaseAdapter(this, list);
		gridView.setAdapter(adapter);

	}
}
