package com.mawujun.mobile.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_task")
public class Task implements IdEntity<String> {
	@Id
	@Column(length=20)
	private String id;//yyyyMMdd+4位流水号
	@Column(length=500)
	private String memo;//任务描述
	@Enumerated(EnumType.STRING)
	private TaskType type;//任务类型
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private TaskStatus status;//任务状态
	
	@Column(length=36)
	private String pole_id;
	@Column(length=50)
	private String pole_name;
	@Column(length=100)
	private String pole_address;
	@Column(length=36)
	private String workunit_id;
	@Column(length=50)
	private String workunit_name;
	@Column(length=36)
	private String customer_id;
	@Column(length=50)
	private String customer_name;
	
	
	private Date createDate;//创建时间
	private Date startHandDate;//开始处理时间，第一次保存的时候
	private Date submitDate;//提交时间
	//private Date approveDate;//管理人员审批时间
	private Date completeDate;//完成时间
	
	private String hitchType;//故障类型
	private String hitchReason;//故障原因，也用来存放维护内容
	
	public String getType_name() {
		if(this.getType()!=null){
			return this.getType().getName();
		}
		return null;
	}
	public String getStatus_name() {
		if(this.getStatus()!=null){
			return this.getStatus().getName();
		}
		return null;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public TaskType getType() {
		return type;
	}
	public void setType(TaskType type) {
		this.type = type;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getStartHandDate() {
		return startHandDate;
	}
	public void setStartHandDate(Date startHandDate) {
		this.startHandDate = startHandDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getHitchType() {
		return hitchType;
	}
	public void setHitchType(String hitchType) {
		this.hitchType = hitchType;
	}
	public String getHitchReason() {
		return hitchReason;
	}
	public void setHitchReason(String hitchReason) {
		this.hitchReason = hitchReason;
	}
	public String getPole_name() {
		return pole_name;
	}
	public void setPole_name(String pole_name) {
		this.pole_name = pole_name;
	}
	public String getPole_address() {
		return pole_address;
	}
	public void setPole_address(String pole_address) {
		this.pole_address = pole_address;
	}
	public String getWorkunit_name() {
		return workunit_name;
	}
	public void setWorkunit_name(String workunit_name) {
		this.workunit_name = workunit_name;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	

}
