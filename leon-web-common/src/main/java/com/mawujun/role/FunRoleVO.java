package com.mawujun.role;

import java.util.ArrayList;
import java.util.List;

public class FunRoleVO {

	private String funId;
	private PermissionEnum permissionEnum; 
	//权限的来源角色
	private List<RoleSource> roleSources=new ArrayList<RoleSource>();
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
	public List<RoleSource> getRoleSources() {
		return roleSources;
	}
	public void setRoleSources(List<RoleSource> roles) {
		this.roleSources = roles;
	}
	public void addRoleSource(RoleSource role) {
		this.roleSources.add( role);//.add(role);
	}
}
