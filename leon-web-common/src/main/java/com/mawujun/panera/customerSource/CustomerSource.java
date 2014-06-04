package com.mawujun.panera.customerSource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_CustomerSource")
public class CustomerSource extends UUIDEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=40)
	private String name;
	
	@Column(length=400)
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
