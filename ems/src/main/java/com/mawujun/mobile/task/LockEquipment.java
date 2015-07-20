package com.mawujun.mobile.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;


@Entity
@Table(name="ems_lockequipment")
public class LockEquipment implements IdEntity<String>{
	@Id
	@Column(length=25)
	private String ecode;
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private LockType lockType;
	@Column(length=36)
	private String type_id;//如果lockType=task，那type_id就是任务id
	private Date createDate;

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.ecode=id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.ecode;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public LockType getLockType() {
		return lockType;
	}

	public void setLockType(LockType lockType) {
		this.lockType = lockType;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
