package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 借转领的通知信息，将通知仓库这些借用单需要填写:领用类型
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_b2inotify")
public class B2INotify extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=36)
	private String ecode;
	@Column(length=36)
	private String store_id;
	@Column(length=36)
	private String borrow_id;
	@Column(length=36)
	private String workunit_id;
	
	@Column(length=36)
	private String pole_id;
	@Column(length=36)
	private String task_id;
	
	
	private Date createDate;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean ishandled=false;//已经处理
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getBorrow_id() {
		return borrow_id;
	}
	public void setBorrow_id(String borrow_id) {
		this.borrow_id = borrow_id;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public Boolean getIshandled() {
		return ishandled;
	}
	public void setIshandled(Boolean ishandled) {
		this.ishandled = ishandled;
	}
}
