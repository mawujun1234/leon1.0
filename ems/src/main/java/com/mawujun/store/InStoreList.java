package com.mawujun.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_instorelist")
public class InStoreList extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=15)
	private String inStore_id;//入库单id
	@Column(length=25)
	private String encode;//设备编码
	
	public String getInStore_id() {
		return inStore_id;
	}
	public void setInStore_id(String inStore_id) {
		this.inStore_id = inStore_id;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}

	

}
