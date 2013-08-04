package com.mawujun.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.role.Role;

@Entity
@Table(name="leon_group")
public class Group extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=30)
	private String name;
	@ManyToOne
	private Group parent;
	@OneToMany(mappedBy="parent")
	private List<Group> children;
	
	//private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public List<Group> getChildren() {
		return children;
	}

	public void setChildren(List<Group> children) {
		this.children = children;
	}
	public void addChild(Group child) {
		if(this.children!=null){
			this.children=new ArrayList<Group>();
		}
		this.children.add(child);
	}

//	public List<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<Role> roles) {
//		this.roles = roles;
//	}
//	
//	public void addRole(Role role) {
//		if(this.roles==null){
//			this.roles=new ArrayList<Role>();
//		}
//		this.roles.add(role);
//	}

}
