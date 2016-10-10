package com.example.myandroid;


import com.example.myandroid.Base.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class flash extends BaseActivity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash);
		imageView = (ImageView) findViewById(R.id.flash);
//		imageView.setBackgroundResource(R.drawable.aaa);
		AlphaAnimation alp = new AlphaAnimation(0, 1);
		alp.setDuration(1000);
		imageView.setAnimation(alp);
		alp.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(MainActivity.class);
				finish();
			}
		});
	}

}
