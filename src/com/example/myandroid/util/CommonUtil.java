package com.example.myandroid.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	private static DecimalFormat df = new DecimalFormat("#.00");// 对数据进行括号内格式规定

	// public static String getFileInfo(long file) {
	// StringBuffer buffer = new StringBuffer();
	// if (file < 1024) {
	// buffer.append(file);
	// buffer.append("B");
	// } else if (file < 1048576) {
	// buffer.append(df.format((double) (file / 1024)));
	// buffer.append("K");
	// } else if (file < 1073741824) {
	// buffer.append(df.format((double) (file / 1048576)));
	// buffer.append("M");
	// } else if (file < 1099511627776l) {
	// buffer.append(df.format((double) (file / 1073741824)));
	// buffer.append("G");
	// }
	// return buffer.toString();
	//
	// }

	public static String getStrTime(long filename) {
		if (filename == 0) {
			return "未知";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String ftime = dateFormat.format(new Date(filename));
		return ftime;

	}

	public static String getFileInfo(long filesize) {
		StringBuffer mstrbuf = new StringBuffer();
		if (filesize < 1024) {
			mstrbuf.append(filesize);
			mstrbuf.append(" B");
		} else if (filesize < 1048576) {
			mstrbuf.append(df.format((double) filesize / 1024));
			mstrbuf.append(" K");
		} else if (filesize < 1073741824) {
			mstrbuf.append(df.format((double) filesize / 1048576));
			mstrbuf.append(" M");
		} else {
			mstrbuf.append(df.format((double) filesize / 1073741824));
			mstrbuf.append(" G");
		}
		return mstrbuf.toString();
	}

}
