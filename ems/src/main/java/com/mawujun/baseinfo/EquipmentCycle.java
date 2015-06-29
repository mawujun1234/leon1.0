package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_equipmentcycle")
public class EquipmentCycle extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length=25)
	private String ecode;
	
	private Date operateDate;//操作时间
	
	@Column(length=36)
	private String operater_id;//操作者id
	@Column(length=36)
	private String operater_name;//操作者名称
	
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private OperateType operateType;//操作类型，入库，出库，领用，返库等等
	@Column(length=36)
	private String type_id;//入库id，领用单id等等 各种单据id,还有任务id
	
	@Column(length=36)
	private String target_id;//目标id。仓库id，作业单位id，维修中心id等,维修出库的时候，就是仓库id，维修中心入库的时候就是维修中心id
	@Column(length=50)
	private String target_name;
	
	/**
	 * 获取生命周期的信息
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public String getCycleInfo(){
		return this.getOperateDate()+",操作类型:"+this.getOperateType().getName()+"("+this.getTarget_id()+")"+",主体:"+this.getTarget_name()+" ,操作者:"+this.getOperater_name();
	}
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getOperater_id() {
		return operater_id;
	}
	public void setOperater_id(String operater_id) {
		this.operater_id = operater_id;
	}
	public String getOperater_name() {
		return operater_name;
	}
	public void setOperater_name(String operater_name) {
		this.operater_name = operater_name;
	}
	public OperateType getOperateType() {
		return operateType;
	}
	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public String getType_id() {
		return type_id;
	}


	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getTarget_name() {
		return target_name;
	}
	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

}
