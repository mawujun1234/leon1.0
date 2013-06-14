package com.mawujun.role;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.exten.TreeNode;

@Entity
@Table(name="leon_Role")
public class Role extends TreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=40)
	private String name;
	@Column(length=100)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(length=10,nullable=false)
	private RoleEnum roleEnum;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Role parent;
	
//	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
//	private List<RoleFunAssociation> roleFunAssociations;
	
	@OneToMany(mappedBy="current",fetch=FetchType.LAZY)
	private List<RoleRole> currents;
	
	@OneToMany(mappedBy="other",fetch=FetchType.LAZY)
	private List<RoleRole> others;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RoleEnum getRoleEnum() {
		return roleEnum;
	}
	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}
	
	public void setRoleEnum(String roleEnum) {
		this.roleEnum = RoleEnum.valueOf(roleEnum);
	}
	
	public String getIconCls() {
		//System.out.println(funEnum);
		if(RoleEnum.roleCategory==roleEnum){
			return "role-category-iconCls";
		} else if(RoleEnum.role==roleEnum){
			return "role-role-iconCls";
		}
		return null;
	}
	public boolean isLeaf() {
		 if(RoleEnum.role==roleEnum){
			return true;
		}
		 return false;
	}
	public Role getParent() {
		return parent;
	}
	public void setParent(Role parent) {
		this.parent = parent;
	}
	public List<RoleRole> getCurrents() {
		return currents;
	}
	public void setCurrents(List<RoleRole> currents) {
		this.currents = currents;
	}
	public List<RoleRole> getOthers() {
		return others;
	}
	public void setOthers(List<RoleRole> others) {
		this.others = others;
	}
	

}
