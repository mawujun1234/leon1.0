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

}
