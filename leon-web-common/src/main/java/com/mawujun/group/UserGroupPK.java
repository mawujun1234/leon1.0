package com.mawujun.group;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable 
public class UserGroupPK  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=36,name="user_id")
	private String userId;
	@Column(length=36,name="group_id")
	private String groupId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
