package com.mawujun.mobile.task;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;


@Entity
@Table(name="ems_overtime")
public class Overtime implements IdEntity<String>,Serializable{
	@Id
	@Column(length=36,updatable=false,unique=true)
	protected String id;
	
	private Integer read=1440;//未阅读超时时间，单位分
	private Integer handling=1440;//处理超时时间，过了多长时间还不处理，单位分
	
	public Integer getRead() {
		return read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	public Integer getHandling() {
		return handling;
	}
	public void setHandling(Integer handling) {
		this.handling = handling;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
