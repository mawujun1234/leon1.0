package com.mawujun.role;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.fun.Fun;
import com.mawujun.repository.idEntity.UUIDEntity;



@Entity
@Table(name = "leon_Role_Fun")
public class RoleFun extends UUIDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Role role;
	@ManyToOne
	private Fun fun;
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private PermissionEnum permissionEnum;
	private Date createDate;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}
	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}
	public void setPermissionEnum(String permissionEnum) {
		this.permissionEnum = PermissionEnum.valueOf(permissionEnum);
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
//	@Transient
//	private String text;//role的名称
//	@Column(name = "role_id")
//	protected String roleId;
//	@Column(name = "fun_id")
//	protected String funId;
//	@Column(length=10)
//	@Enumerated(EnumType.STRING)
//	private PermissionEnum permissionEnum;
//	private Date createDate;
//	
////	@Transient
////	private List<RoleFunAssociation> children=new ArrayList<RoleFunAssociation>();
//	
//
//
//
//	public PermissionEnum getPermissionEnum() {
//		return permissionEnum;
//	}
//
//	public void setPermissionEnum(PermissionEnum permissionType) {
//		this.permissionEnum = permissionType;
//	}
//	
//	public void setPermissionEnum(String permissionType) {
//		this.permissionEnum = PermissionEnum.valueOf(permissionType);
//	}
//
//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String text) {
//		this.text = text;
//	}
//
//	public String getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(String roleId) {
//		this.roleId = roleId;
//	}
//
//	public String getFunId() {
//		return funId;
//	}
//
//	public void setFunId(String funId) {
//		this.funId = funId;
//	}

}
