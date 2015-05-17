package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_customer")
public class Customer extends UUIDEntity {
	@Column(length=30)
	private String name;
	private Integer type;//0 :企业1：机关,2:区(对0和1进行分组的)
	@Column(length=36)
	private String parent_id;
	@Column(length=100)
	private String memo;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

}
