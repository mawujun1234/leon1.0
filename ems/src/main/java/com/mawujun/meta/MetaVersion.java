package com.mawujun.meta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_metaversion")
public class MetaVersion implements IdEntity<String>{
	@Id
	@Column(length=50)
	private String clasName;
	private Integer version;
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.clasName=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return clasName;
	}
	public String getClasName() {
		return clasName;
	}
	public void setClasName(String clasName) {
		this.clasName = clasName;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
