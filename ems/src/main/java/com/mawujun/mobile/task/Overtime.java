package com.mawujun.mobile.task;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;


@Entity
@Table(name="ems_overtime")
public class Overtime extends UUIDEntity {
	
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

}
