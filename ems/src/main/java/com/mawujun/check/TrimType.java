package com.mawujun.check;

public enum TrimType {
	transfer("转移"),uninstall("卸载");
	private String name;
	TrimType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
