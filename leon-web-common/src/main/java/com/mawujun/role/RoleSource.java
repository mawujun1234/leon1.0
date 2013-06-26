package com.mawujun.role;

public class RoleSource {
	private String id;
	private String name;
	private PermissionEnum permissionEnum;
	
	
//	public static RoleSource getInstance(){
//		
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}
	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}

}
