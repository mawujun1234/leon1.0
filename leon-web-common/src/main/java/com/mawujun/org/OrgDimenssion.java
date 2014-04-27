package com.mawujun.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="leon_OrgDimenssion")
public class OrgDimenssion  implements IdEntity<String> {
	private static final long serialVersionUID = 1L;
	@Id
	protected String id;
	@Column(length=30)
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
}
