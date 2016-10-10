package com.example.myandroid.view;

import com.example.myandroid.R;
import com.example.myandroid.R.id;
import com.example.myandroid.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionView extends LinearLayout {
	private ImageView avimage1;
	private ImageView avimage2;
	private TextView avtext;

	public ActionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.actionitem, this);
		avimage1 = (ImageView) findViewById(R.id.avimage1);
		avimage2 = (ImageView) findViewById(R.id.avimage2);
		avtext = (TextView) findViewById(R.id.avtext);
	}

	public void initactionview(String title, int leftid, int rightid,
			OnClickListener onClickListener) {
		avtext.setText(title);
		if (leftid != -1) {
			avimage1.setBackgroundResource(leftid);
			avimage1.setOnClickListener(onClickListener);
		} else {
			avimage1.setVisibility(View.INVISIBLE);
		}
		if (rightid != -1) {
			avimage2.setBackgroundResource(rightid);
			avimage2.setOnClickListener(onClickListener);
		} else {
			avimage2.setVisibility(View.INVISIBLE);
		}

	}

}
