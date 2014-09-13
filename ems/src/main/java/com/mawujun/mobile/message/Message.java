package com.mawujun.mobile.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 发送给 作业单位的消息
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_message")
public class Message extends UUIDEntity {
	@Column(length=36)
	private String task_id;
	@Column(length=36)
	private String workunit_id;
	@Enumerated(EnumType.STRING)
	private MessageType type;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isNew;//是否是新消息
	@Column(length=500)
	private String content;
	
	private Date createDate;
	private Date readDate;//第一次查看时间
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}


}
