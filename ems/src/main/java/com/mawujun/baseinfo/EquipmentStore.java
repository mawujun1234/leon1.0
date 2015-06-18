package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 设备在仓库的情况
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name = "ems_equipment_store")
@IdClass(EquipmentStorePK.class)
public class EquipmentStore implements IdEntity<EquipmentStorePK> {
//	@EmbeddedId  
//	private EquipmentStorePK id;
	@Id
	@Column(length=36)
	private String ecode;//条码ecode或品名id
	@Id
	@Column(length=36)
	private String store_id;
	
	private Integer num=1;//入库的数量，默认是1
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentStoreType type;//新品入库，还是返回入库，还是借用归还，还是调拨入库
	@Column(length=36)
	private String type_id;//如果是新品入库，就是新品入库的单据id，如果是领用返就是领用id，借用就是借用id
	@Column(length=36)
	private String from_id;//来源的id，比如从仓库来，就是仓库id，如果是从杆位上来就是杆位id
	//@Column(updatable=false)
	private Date inDate;//入库的时间
	
	
	public EquipmentStorePK getId() {
		return new EquipmentStorePK(ecode,store_id);
	}
	public void setId(EquipmentStorePK id) {
		//this.id = id;
		this.ecode=id.getEcode();
		this.store_id=id.getStore_id();
	}
	public void setId(String  ecode,String store_id) {
		//this.id = id;
		this.ecode=ecode;
		this.store_id=store_id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public EquipmentStoreType getType() {
		return type;
	}
	public void setType(EquipmentStoreType type) {
		this.type = type;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	
}
