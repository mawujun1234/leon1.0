package com.mawujun.mobile.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 这个是这次任务操作的设备列表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_taskequipmentlist",uniqueConstraints=@UniqueConstraint(columnNames={"task_id","ecode"}))
public class TaskEquipmentList extends UUIDEntity {
	@Column(length=36)
	private String task_id;
	@Column(length=36)
	private String ecode;
	//private Date scanDate;//扫描的时间
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private TaskListTypeEnum type;
	//提交任务的时候设备的状态，主要用于维修的时候
	//private Integer equipment_status;
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EquipmentStatus equipment_status;//安装或维修前的设备状态，这个字段可以删除，没设么用了
	
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public TaskListTypeEnum getType() {
		return type;
	}
	public void setType(TaskListTypeEnum type) {
		this.type = type;
	}
	public EquipmentStatus getEquipment_status() {
		return equipment_status;
	}
	public void setEquipment_status(EquipmentStatus equipment_status) {
		this.equipment_status = equipment_status;
	}


}
