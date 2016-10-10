package com.example.myandroid.bean;

public class ClassInfo {

	private String name;
	private int idx;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public ClassInfo(String name, int idx) {
		this.name = name;
		this.idx = idx;
	}

}
