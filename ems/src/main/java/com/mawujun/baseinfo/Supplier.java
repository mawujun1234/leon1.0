package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_supplier")
public class Supplier implements IdEntity<String>{
	@Id
	@Column(length=3)
	private String id;
	@Column(length=30)
	private String name;
	@Column(length=30)
	private String sname;//简称
	@Column(length=30)
	private String website;//网址
	@Column(length=100)
	private String memo;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	

}
