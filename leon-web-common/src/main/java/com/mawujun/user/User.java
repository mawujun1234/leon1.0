package com.mawujun.user;


import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.role.Role;

@Entity
@Table(name="leon_User")
public class User extends UUIDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=20)
	private String loginName;
	@Column(length=20,updatable=false)
	private String password;
	@Column(length=20)
	private String name;
	
	private boolean deleted;
	private Date deletedDate;
	
	private boolean enable;
	private boolean locked;
	
	@Column(updatable=false)
	private Date createDate;
	private Date expireDate;
	
	private Date lastLoginDate;
	private String lastIp;
	
	public User(){}
	
	public User(String id){
		this.id=id;
	}
	
//	@Transient
//	private Set<Role> roles;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnable() {
		return enable;
	}
	public boolean isAdmin() {
		return "admin".equalsIgnoreCase(loginName);
	}
	public boolean isAccountExpired() {
		if(expireDate!=null){
			if(expireDate.getTime()<(new Date()).getTime()){
				return true;
			}
		}
		return false;
	}

	public void setEnable(boolean isEnable) {
		this.enable = isEnable;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean isLocked) {
		this.locked = isLocked;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
}
