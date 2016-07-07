package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;

//@Entity
//@Table(name="ems_area")
public class Area extends UUIDEntity {
	@Column(length=30)
	private String name;
	@Column(length=100)
	private String memo;
	
//	@Column(length=36)
//	private String workunit_id;
//	@Transient
//	private String workunit_name;


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




}
