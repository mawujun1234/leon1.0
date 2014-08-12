package com.mawujun.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="sys_userstore",uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "store_id"})})
public class UserStore extends UUIDEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=36)
	private String user_id;
	@Column(length=36)
	private String store_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

}
