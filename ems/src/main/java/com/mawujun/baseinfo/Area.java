package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_area")
public class Area extends UUIDEntity {
	@Column(length=30)
	private String name;
	
	@Column(length=36)
	private String workunit_id;

	public String getWorkunit_id() {
		return workunit_id;
	}

	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}

}
