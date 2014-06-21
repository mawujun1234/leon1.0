package com.mawujun.panera.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.panera.customerProperty.CustomerProperty;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="panera_Contact")
public class Contact extends UUIDEntity {
	@Column(length=100)
	private String name;
	@Column(length=100)
	private String position;
	@Column(length=100)
	private String email;
	@Column(length=30)
	private String phone;
	@Column(length=30)
	private String fax;
	@Column(length=30)
	private String mobile;
	@Column(length=100)
	private String chatNum;//聊天工具账号
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChatNum() {
		return chatNum;
	}

	public void setChatNum(String chatNum) {
		this.chatNum = chatNum;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
