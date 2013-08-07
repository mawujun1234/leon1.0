package com.mawujun.group;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable 
public class GroupRolePK  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=36,name="role_id")
	private String roleId;
	@Column(length=36,name="group_id")
	private String groupId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	


}
