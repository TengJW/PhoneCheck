package com.example.myandroid.util;

import com.example.myandroid.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
/**
 * 系统通知栏通知
 * @author Administrator
 *
 */
public class NotificationUtil {
	public static Notification notification;
	public static NotificationManager manager;
	public static final int NOTIFI_APPICON_ID = 1;

	public static void startNotification(Context context) {
		if (notification == null) {
			notification = new Notification();
		}
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "新通知！";
		notification.when = System.currentTimeMillis();

		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.layout_notification_appicon);
		notification.contentView = rv;

		Intent i = new Intent("com.example.myandroid");//隐式跳转
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i,
				PendingIntent.FLAG_UPDATE_CURRENT);//intent加强版可以从进程之间跳转
		notification.contentIntent = pendingIntent;
		if (manager == null) {
			manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		manager.notify(NOTIFI_APPICON_ID, notification);
	}

	public static void cancleNotification(Context context) {
		if (manager == null) {
			manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		manager.cancel(NOTIFI_APPICON_ID);
	}
}
