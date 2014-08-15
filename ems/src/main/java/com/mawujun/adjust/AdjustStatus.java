package com.mawujun.adjust;

public enum AdjustStatus {
	edit("编辑中"),carry("调拨途中"),over("完成");
	private String name;
	AdjustStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
