package com.mawujun.role;

public enum PermissionEnum {
	PUBLIC(1,"公有"),PRIVATE(0,"私有"),DENY(-1,"拒绝"); 
	
	private String text;
	private int code;
	
	public String getText() {
		return text;
	}
	public int getCode() {
		return code;
	}

	private PermissionEnum(int code,String t)
	{
		this.code=code;
		this.text=t;
	}
}
