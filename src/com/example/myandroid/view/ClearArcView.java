package com.example.myandroid.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ClearArcView extends View {
	private Paint paint = new Paint();// ����
	private RectF ovel;// ��Բ��
	private int arcColor = 0xFFFF8C00; // ���ʵ���ɫ
	private static int START_ANGLE = -90;// ��ʼ�ĽǶ�
	private int sweep_angle = 0;// �����ĽǶ�
	private int state = 0;
	private int[] back = new int[] { -5, -5, -5, -5, -7, -8, -8 };
	private int backindex;
	private int[] goon = new int[] { 4, 4, 6, 7, 8, 6, 1, 4, 5 };
	private int goonindex;
	private boolean isRunning = false;

	public ClearArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAngle(360);
	}

	public void setAngle(int anghe) {
		sweep_angle = anghe;
		postInvalidate();// ˢ�½���
	}

	/**
	 * �����ؼ��Ĵ�С���
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		ovel = new RectF(0, 0, width, height);// ������ø�������
	}

	/**
	 * ��ʼ�ڻ����ϻ�ͼ
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(arcColor);
		paint.setAntiAlias(true);
		canvas.drawArc(ovel, START_ANGLE, sweep_angle, true, paint);
	}

	public void setAngleWidthAnim(final int angle) {
		if (isRunning) {
			return;
		}
		isRunning = true;
		state = 0;
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				switch (state) {
				case 0:
					sweep_angle += back[backindex++];
					if (backindex >= back.length) {
						backindex = back.length - 1;
					}
					postInvalidate();
					if (sweep_angle <= 0) {
						sweep_angle = 0;
						state = 1;
						backindex = 0;
					}
					break;
				case 1:
					sweep_angle += goon[goonindex++];
					if (goonindex >= goon.length) {
						goonindex = goon.length - 1;
					}
					postInvalidate();
					if (sweep_angle >= angle) {
						sweep_angle = angle;
						goonindex = 0;
						isRunning = false;
						timer.cancel();
					}
					break;
				}
			}
		};
		timer.schedule(task, 20, 20);
	}
}
