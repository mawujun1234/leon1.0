package com.mawujun.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_Org")
public class Org extends UUIDEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=60)
	private String name;
	@Column(length=60)
	private String code;//编码
	@Column(length=60)
	private String reportCode;
	@Column(length=20)
	private String phonenumber;//电话号码
	@Column(length=60)
	private String fax;//传真
	@Column(length=120)
	private String address;//地址
	@Column(length=20)
	private String postalcode;//邮编
	@Column(length=30)
	private String corporation;//法人代表
	@Column(length=60)
	private String email;//
	@Column(length=120)
	private String web;//网址
	@Column(length=600)
	private String introduction;//简介
	
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	private OrgType orgType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public OrgType getOrgType() {
		return orgType;
	}

	public void setOrgType(OrgType orgType) {
		this.orgType = orgType;
	}
	

}
