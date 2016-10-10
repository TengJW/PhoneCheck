package com.example.myandroid.bean;

public class TableClass {
	private String name;
	private long number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public TableClass(String name, long number) {
		this.name = name;
		this.number = number;
	}

}
