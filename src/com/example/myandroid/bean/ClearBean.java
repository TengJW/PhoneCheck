package com.example.myandroid.bean;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ClearBean {
	private int id;
	private String softChinesename;
	private String softEnglishname;
	private String apkname;
	private String filepath;
	private Drawable icon;
	private long size;
	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public ClearBean(int id, String softChinesename, String softEnglishname,
			String apkname, String filepath) {
		super();
		this.id = id;
		this.softChinesename = softChinesename;
		this.softEnglishname = softEnglishname;
		this.apkname = apkname;
		this.filepath = filepath;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSoftChinesename() {
		return softChinesename;
	}

	public void setSoftChinesename(String softChinesename) {
		this.softChinesename = softChinesename;
	}

	public String getSoftEnglishname() {
		return softEnglishname;
	}

	public void setSoftEnglishname(String softEnglishname) {
		this.softEnglishname = softEnglishname;
	}

	public String getApkname() {
		return apkname;
	}

	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
