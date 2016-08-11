package com.mawujun.baseinfo;

public enum PoleType {
	pole("点位"),machineroom("机房"),kakou("卡口");
	private String name;
	PoleType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
