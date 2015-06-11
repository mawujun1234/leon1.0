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
@Table(name = "ems_equipment_repair")
@IdClass(EquipmentRepairPK.class)
public class EquipmentRepair implements IdEntity<EquipmentRepairPK> {
//	@EmbeddedId  
//	private EquipmentStorePK id;
	@Id
	@Column(length=36)
	private String ecode;//条码ecode或品名id
	@Id
	@Column(length=36)
	private String repair_id;
	
	private Integer num=1;//入库的数量，默认是1
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentRepairType type;//新品入库，还是返回入库，还是借用归还，还是调拨入库
	@Column(length=36)
	private String type_id;//如果是新品入库，就是新品入库的单据id，如果是领用返就是领用id，借用就是借用id
	//@Column(updatable=false)
	private Date inDate;//入库的时间
	
	public EquipmentRepairPK getId() {
		return new EquipmentRepairPK(ecode,repair_id);
	}
	public void setId(EquipmentRepairPK id) {
		//this.id = id;
		this.ecode=id.getEcode();
		this.repair_id=id.getRepair_id();
	}
	public void setId(String  ecode,String repair_id) {
		//this.id = id;
		this.ecode=ecode;
		this.repair_id=repair_id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public EquipmentRepairType getType() {
		return type;
	}
	public void setType(EquipmentRepairType type) {
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
	public String getRepair_id() {
		return repair_id;
	}
	public void setRepair_id(String repair_id) {
		this.repair_id = repair_id;
	}
	
}
