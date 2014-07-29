package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_brand")
public class Brand implements IdEntity<String>{
	@Id
	@Column(length=3)
	private String id;
	@Column(length=30)
	private String name;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
