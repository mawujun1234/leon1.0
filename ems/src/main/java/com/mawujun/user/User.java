package com.mawujun.user;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.meta.MetaVersion;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="sys_user")
public class User extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=30)
	private String username;
	@Column(length=30,updatable=false)
	private String password;
	@Column(length=15)
	private String name;
	@Column(length=20)
	private String phone;
	@Column(length=50)
	private String email;
	@Column(length=100)
	private String address;
	@Column(nullable=false)
	private Integer type=0;//类型。0：仓管员或管理员等.非操作员。1:操作员
	private Date loginDate;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
	//判断是不是worunit的账号
	@Transient
	private Boolean isWorkunit=false;
	//@Transient
	//private Map<String,Integer> metaVersion;
	
	public String toString(){
		return username;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getIsWorkunit() {
		return isWorkunit;
	}

	public void setIsWorkunit(Boolean isWorkunit) {
		this.isWorkunit = isWorkunit;
	}


}
