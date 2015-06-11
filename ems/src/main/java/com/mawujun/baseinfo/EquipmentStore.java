package com.mawujun.baseinfo;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 设备在仓库的情况
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
//@Entity
//@Table(name = "customer")
//@IdClass(EquipmentStorePK.class)
public class EquipmentStore {
	private String encode;
	private String store_id;
	
	private String num;
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
