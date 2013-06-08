package com.mawujun.role;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.exten.TreeNode;



@Entity
@Table(name = "leon_Role_Fun")
public class RoleFunAssociation extends TreeNode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String text;//role的名称
	@Column(name = "role_id")
	protected String roleId;
	@Column(name = "fun_id")
	protected String funId;
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private PermissionTypeEnum permissionType;
	//@Column()
	private Date createDate;
	
	@Transient
	private List<RoleFunAssociation> children=new ArrayList<RoleFunAssociation>();
	



	public PermissionTypeEnum getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionTypeEnum permissionType) {
		this.permissionType = permissionType;
	}
	
	public void setPermissionType(String permissionType) {
		this.permissionType = PermissionTypeEnum.valueOf(permissionType);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFunId() {
		return funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public List<RoleFunAssociation> getChildren() {
		return children;
	}

	public void setChildren(List<RoleFunAssociation> children) {
		this.children = children;
	}
	
	public void addChildren(RoleFunAssociation child) {
		this.children.add(child);
	}



}
