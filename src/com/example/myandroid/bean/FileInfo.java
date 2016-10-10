package com.example.myandroid.bean;

import java.io.File;

public class FileInfo {
	private File file;
	private boolean isSelect;
	private String icon;// 图像文件名称
	private String type;// 文件类型
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileInfo(File a, String icon, String filetap) {
		super();
		this.file = a;
		this.icon = icon;
		this.type = filetap;
	}

	public File getA() {
		return file;
	}

	public void setA(File a) {
		this.file = a;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFileType() {
		return type;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileType(String filetap) {
		this.type = filetap;
	}

}
