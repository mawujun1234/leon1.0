package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_store")
public class Store extends UUIDEntity {
	@Column(length=30)
	private String name;
	
	private Integer type;//1:在建仓库，2：维修中心,3:备品备件仓库
	@Column(length=100)
	private String memo;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
	public String getType_name() {
		if(this.getType()==1){
			return "在建仓库";
		} else if(this.getType()==2){
			return "维修中心";
		}else if(this.getType()==3){
			return "备品备件库";
		} else {
			return null;
		}
	}
	
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
