package com.mawujun.store;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_outstore")
public class OutStore   implements IdEntity<String>{
	@Id
	@Column(length=15)
	private String id;//出库单号，年日日时分秒
	@Column(length=36)
	private String store_id;//出库仓库id
	private String operater;//仓管id
	private Date operateDate;//入库时间
	@Column(length=36)
	private String workunit_id;//出库仓库id
	
	private Integer type;//出库类型 1：设备领用
	@Column(length=100)
	private String memo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

}
