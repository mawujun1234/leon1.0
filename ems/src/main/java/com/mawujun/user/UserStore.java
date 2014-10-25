package com.mawujun.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean look;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean edit;
	

	@Transient
	private String store_name;
	@Transient
	private Boolean store_status;
	@Transient
	private Integer store_type;
	
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
	public Boolean getLook() {
		return look;
	}
	public void setLook(Boolean look) {
		this.look = look;
	}
	public Boolean getEdit() {
		return edit;
	}
	public void setEdit(Boolean edit) {
		this.edit = edit;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public Boolean getStore_status() {
		return store_status;
	}
	public void setStore_status(Boolean store_status) {
		this.store_status = store_status;
	}
	
	public String getStore_typeName() {
		if(this.getStore_type()==1){
			return "在建仓库";
		} else if(this.getStore_type()==2){
			return "维修中心";
		} else if(this.getStore_type()==3){
			return "备品备件仓库";
		}else {
			return null;
		}
	}
	public Integer getStore_type() {
		return this.store_type;
	}
	public void setStore_type(Integer store_type) {
		this.store_type = store_type;
	}

}
