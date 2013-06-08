package com.mawujun.role;

public enum PermissionTypeEnum {
	publicP("公有权限"),privateP("私有权限"); 
	
	private String text;
	
	public String getText() {
		return text;
	}

	private PermissionTypeEnum(String t)
	{
		this.text=t;
	}
}
