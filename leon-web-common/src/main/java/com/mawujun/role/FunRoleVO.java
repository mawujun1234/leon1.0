package com.mawujun.role;

import java.util.List;

public class FunRoleVO {

	private String funId;
	private PermissionEnum permissionEnum; 
	private List<Role> roles;
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}
	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}
	public void setPUBLIC() {
		this.permissionEnum = PermissionEnum.PUBLIC;
	}
	public void setPRIVATE() {
		this.permissionEnum = PermissionEnum.PRIVATE;
	}
	public void setDENY() {
		this.permissionEnum = PermissionEnum.DENY;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRoles(Role role) {
		this.roles.add(0, role);//.add(role);
	}
}
