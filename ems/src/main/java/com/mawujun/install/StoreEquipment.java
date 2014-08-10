package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 仓库存放的设备
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="ems_storeequipment")
public class StoreEquipment  extends UUIDEntity{
	@Column(length=36)
	private String store_id;
	@Column(length=25)
	private String ecode;
//	@Column(length=15)
//	private String inStore_id;//入库单号
	
	private Integer num;//产品的库存数量
//	//下面的都是最新一次的入库
//		private Date inDate;//入库时间
//	private Integer inStore_type;//入库类型 1：新品入库，2：设备返库，3：维修入库
//	@Column(length=36)
//	private String storeman_id;//仓管id
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	


}
