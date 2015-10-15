package com.mawujun.store;

public enum OrderType {
	old_equipment("旧品订单"),new_equipment("新品订单");
	
	private String name;
	OrderType(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
