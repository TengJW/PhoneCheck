package com.example.myandroid.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.myandroid.bean.ClassInfo;
import com.example.myandroid.bean.TableClass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManager {

	private static final String FILE_NAME = "commonnum.db";
	private static final String FILE_PACKAGE = "com.example.myandroid";
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ FILE_PACKAGE + "/" + FILE_NAME;

	public static void readUpdateDB(InputStream path) throws IOException {
		File tofile = new File(FILE_PATH);
		if (!tofile.exists()) {
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
	}

	public static List<ClassInfo> readClassListTable() {
		List<ClassInfo> classInfo = new ArrayList<ClassInfo>();
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				FILE_PATH, null);
		String sql = "select * from classlist";
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int idx = cursor.getInt(cursor.getColumnIndex("idx"));
				ClassInfo classInfo2 = new ClassInfo(name, idx);
				classInfo.add(classInfo2);
			} while (cursor.moveToNext());
			cursor.close();
			database.close();
		}
		return classInfo;
	}
/**
 * 根据传递过来的表名查询表中的数据
 * @param tableName
 * @return
 */
	public static List<TableClass> readTableClass(String tableName) {
		List<TableClass> list = new ArrayList<TableClass>();
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
				FILE_PATH, null);
		String sql = "select * from " + tableName;
		Cursor cursor = database.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				long number = cursor.getLong(cursor.getColumnIndex("number"));
				TableClass classInfo2 = new TableClass(name, number);
				list.add(classInfo2);
			} while (cursor.moveToNext());
			cursor.close();
			database.close();
		}
		return list;
	}
}
