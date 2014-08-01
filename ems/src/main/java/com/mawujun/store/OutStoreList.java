package com.mawujun.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_outstorelist")
public class OutStoreList  extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=15)
	private String outStore_id;//入库单id
	@Column(length=20)
	private String ecode;//设备编码
	public String getOutStore_id() {
		return outStore_id;
	}
	public void setOutStore_id(String outStore_id) {
		this.outStore_id = outStore_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

}
