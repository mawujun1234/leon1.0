package com.mawujun.panera.customerSource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="panera_customersource")
public class CustomerSource extends UUIDEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=40)
	private String name;
	
	@Column(length=400)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
