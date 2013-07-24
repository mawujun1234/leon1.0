package com.mawujun.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.AutoIdEntity;

@Entity
@Table(name="t_parent")
public class Parent extends AutoIdEntity<Integer> {
	@Column(length=40)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
