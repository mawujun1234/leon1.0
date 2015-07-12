package com.mawujun.repair;

public enum ScrapStatus {
	scrap_confirm("报废确认中"),scrap("报废");

	private String name;
	private ScrapStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
