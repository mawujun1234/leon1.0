package com.mawujun.store;

public enum OrderStatus {
	edit("编辑中"),editover("已确认");
	
	private String name;
	OrderStatus(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
