package com.example.myandroid.Base;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.myandroid.R;
import com.example.myandroid.view.ActionView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseActivity extends Activity {
	/**
	 * ��ǰ��������װ���е�activity
	 */
	private static ArrayList<BaseActivity> onlinActivities = new ArrayList<BaseActivity>();

	/**
	 * һ���������е�activity
	 */
	public void finishAllActivity() {
		Iterator<BaseActivity> iterator = onlinActivities.iterator();
		while (iterator.hasNext()) {
			iterator.next().finish();

		}
	}

	/**
	 * ��װһ��ActionBarView
	 * 
	 * @param ActionID
	 *            ActionView�ؼ���ID
	 * @param title
	 * @param leftid
	 * @param rightid
	 * @param clickListener
	 */
	protected void initActionBar(String title, int leftid, int rightid,
			OnClickListener clickListener) {
		ActionView actionView = (ActionView) findViewById(R.id.actionview);
		actionView.initactionview(title, leftid, rightid, clickListener);
	}

	/**
	 * ��װһ����ת
	 */

	protected void startActivity(Class<?> toclass) {
		Intent intent = new Intent(this, toclass);
		startActivity(intent);
	}

	protected void startActivity(Class<?> toclass, Bundle bundle) {
		Intent intent = new Intent(this, toclass);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * ���õ�ǰ����ҳ�浯������
	 */

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onlinActivities.add(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onlinActivities.remove(this);
	}

	protected Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			myHandlerMessage(msg);
		}

	};

	protected void myHandlerMessage(Message msg) {

	}

}
