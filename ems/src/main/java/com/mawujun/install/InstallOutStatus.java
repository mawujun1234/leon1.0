package com.mawujun.install;

public enum InstallOutStatus {
	edit("编辑中"),over("已出库");
	private String name;
	InstallOutStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
