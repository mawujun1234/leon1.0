package com.mawujun.mobile.check;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;
/**
 * 盘点单
 * @author mawujun
 *
 */
@Entity
@Table(name="ems_check")
public class Check implements IdEntity<Integer>{
	@Id
	private Integer id;//20161111080808这种样子
	@Enumerated(EnumType.STRING)
	private CheckStatus status;
	@Column(length=50)
	private String creater;//创建者
	private Date createDate;//创建时间
	@Column(length=50)
	private String completer;//完成者
	private Date completeDate;//完成时间
	@Column(length=36)
	private String task_id;//任务id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CheckStatus getStatus() {
		return status;
	}
	public void setStatus(CheckStatus status) {
		this.status = status;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCompleter() {
		return completer;
	}
	public void setCompleter(String completer) {
		this.completer = completer;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
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
	

}
