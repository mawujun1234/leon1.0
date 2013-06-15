package com.mawujun.role;

import java.util.ArrayList;
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
	
	//所对应的都是父角色或者是护斥的角色
	@OneToMany(mappedBy="current",fetch=FetchType.LAZY)
	private List<RoleRole> currents=new ArrayList<RoleRole>();
	
	//所对应的都是子角色或者是护斥的角色
	@OneToMany(mappedBy="other",fetch=FetchType.LAZY)
	private List<RoleRole> others=new ArrayList<RoleRole>();
	
	public Role(){
		
	}
	public Role(String id){
		this.id=id;
	}
	
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
	/**
	 * 判断参数节点是否是当前节点的祖先节点了
	 * @author mawujun 16064988@qq.com 
	 * @param parent
	 * @return
	 */
	public boolean hasAncestor(Role parent){
		if(this.getId().equals(parent.getId())){
			return true;
		}
		for(RoleRole roleRole:this.others) {
			if(roleRole.getRoleRoleEnum()==RoleRoleEnum.inherit){
				if(roleRole.getParent().getId().equals(parent.getId())){
					return true;
				} else {
					return roleRole.getParent().hasChild(parent);
				}
			}
		}
		return false;
	}
	/**
	 * 判断参数节点是否是当前节点的子孙节点了
	 * @author mawujun 16064988@qq.com 
	 * @param parent
	 * @return
	 */
	public boolean hasChild(Role child){
		if(this.getId().equals(child.getId())){
			return true;
		}
		for(RoleRole roleRole:this.currents) {
			if(roleRole.getRoleRoleEnum()==RoleRoleEnum.inherit){
				if(roleRole.getChild().getId().equals(child.getId())){
					return true;
				} else {
					return roleRole.getChild().hasChild(child);
				}
			}
			
		}
		return false;
	}
	
	public boolean hasMutex(Role mutex){
		for(RoleRole roleRole:this.currents) {
			if(roleRole.getRoleRoleEnum()==RoleRoleEnum.mutex){
				if(roleRole.getOther().getId().equals(mutex.getId())){
					return true;
				} else {
					return roleRole.getChild().hasChild(mutex);
				}
			}
			
		}
	}

}
