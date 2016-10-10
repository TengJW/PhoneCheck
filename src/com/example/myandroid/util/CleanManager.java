package com.example.myandroid.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.myandroid.R;
import com.example.myandroid.bean.ClassInfo;
import com.example.myandroid.bean.ClearBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class CleanManager {

	private static final String FILE_NAME = "clearpath.db";
	private static final String FILE_PACKAGE = "com.example.myandroid";
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ FILE_PACKAGE + "/" + FILE_NAME;

	public static ArrayList<ClearBean> softFileList;

	public static List<ClearBean> getPhoneSoftDetial(Context context) {
		if (softFileList == null) {
			softFileList = readClassListTable();
		}
		ArrayList<ClearBean> currentPhoneDetialList = new ArrayList<ClearBean>();
		for (ClearBean cb : softFileList) {
			System.out.println("---------------" + cb.getFilepath());
			File file = new File(cb.getFilepath());
			Drawable icon;
			if (file.exists()) {
				try {
					icon = context.getPackageManager().getApplicationIcon(
							cb.getApkname());
				} catch (Exception e) {
					icon = context.getResources().getDrawable(
							R.drawable.ic_launcher);
				}
				cb.setIcon(icon);
				currentPhoneDetialList.add(cb);
			}
		}
		return currentPhoneDetialList;

	}

	// private static List<ClearBean> classInfo = null;

	public static void readclearDB(InputStream path) throws IOException {
		File tofile = new File(FILE_PATH);
		if (!tofile.exists()) {
			return;
		}
		BufferedInputStream bis = new BufferedInputStream(path);
		FileOutputStream fis = new FileOutputStream(tofile);
		BufferedOutputStream bos = new BufferedOutputStream(fis);
		int length = 0;
		byte[] b = new byte[5 * 1024];
		while ((length = bis.read(b)) != -1) {
			bos.write(b, 0, length);
		}
		bos.flush();
		bos.close();
		bis.close();
	}

	public static ArrayList<ClearBean> readClassListTable() {
		ArrayList<ClearBean> classInfo = new ArrayList<ClearBean>();
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				FILE_PATH, null);
		String sql = "select * from softdetail ";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				String softchinesename = cursor.getString(cursor
						.getColumnIndex("softChinesename"));
				String softenglishname = cursor.getString(cursor
						.getColumnIndex("softEnglishname"));
				String apkname = cursor.getString(cursor
						.getColumnIndex("apkname"));
				String filepath = cursor.getString(cursor
						.getColumnIndex("filepath"));
				filepath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + filepath;
				ClearBean bean = new ClearBean(id, softchinesename,
						softenglishname, apkname, filepath);
				classInfo.add(bean);

			} while (cursor.moveToNext());
			cursor.close();
			database.close();
		}
		return classInfo;
	}
}
