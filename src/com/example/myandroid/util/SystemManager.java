package com.example.myandroid.util;

import android.os.Build;
import android.text.style.BulletSpan;

public class SystemManager {

	public static String getPhoneName() {

		return Build.BRAND;
	}

	public static String getSystemPhoneModel() {
		return Build.VERSION.RELEASE;
	}

	public static String getPhoneModel() {
		return Build.MODEL + " Android " + getSystemPhoneModel();
	}

}
