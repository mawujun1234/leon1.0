package com.mawujun.baseinfo;

public enum PoleType {
	pole("点位"),machineroom("机房");
	private String name;
	PoleType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
