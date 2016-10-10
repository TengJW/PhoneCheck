package com.example.myandroid.bean;

import android.content.pm.PackageInfo;

public class AppInfo {
	private PackageInfo packageInfo;
	private boolean isDel;
	private boolean isClear;

	public boolean isClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	public AppInfo(PackageInfo packageInfo, boolean isdel) {
		super();
		this.packageInfo = packageInfo;
		this.isDel = isdel;
	}

	public AppInfo(PackageInfo packageInfo) {
		super();
		this.packageInfo = packageInfo;
	}

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

}
