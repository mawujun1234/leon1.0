package com.mawujun.install;

public enum BorrowStatus {
	edit("编辑中"),noreturn("未归还"),over("完成");
	private String name;
	BorrowStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
