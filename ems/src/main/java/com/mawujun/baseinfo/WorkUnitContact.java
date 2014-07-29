package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_workunit_contact")
public class WorkUnitContact extends UUIDEntity {
	
	@Column(length=15)
	private String contact;//联系人
	@Column(length=20)
	private String phone;
	@Column(length=15)
	private String mobile;
	@Column(length=30)
	private String position;
	@Column(length=60)
	private String email;
	@Column(length=20)
	private String fax;
	@Column(length=80)
	private String address;
	@Column(length=15)
	private String postcode;
	@Column(length=36)
	private String workunit_id;
	
	
	
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}


}
