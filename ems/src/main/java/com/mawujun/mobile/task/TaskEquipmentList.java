package com.mawujun.mobile.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	private Date scanDate;//扫描的时间
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private TaskListTypeEnum type;
	
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
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public TaskListTypeEnum getType() {
		return type;
	}
	public void setType(TaskListTypeEnum type) {
		this.type = type;
	}

}
