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
	@Column(length=15)
	private String id;//年月日+4位流水号
	@Column(length=500)
	private String memo;//任务描述
	@Enumerated(EnumType.STRING)
	private TaskTypeEnum type;//任务类型
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private TaskStatusEnum status;//任务状态
	
	@Column(length=36)
	private String pole_id;
	@Column(length=36)
	private String workunit_id;
	@Column(length=36)
	private String customer_id;
	
	
	private Date createDate;//创建时间
	private Date startHandDate;//开始处理时间，第一次保存的时候
	private Date submitDate;//提交时间
	private Date approveDate;//管理人员审批时间
	private Date completeDate;//完成时间
	
	private String hitchType;//故障类型
	private String hitchReason;//故障原因，也用来存放维护内容
	
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
	public TaskTypeEnum getType() {
		return type;
	}
	public void setType(TaskTypeEnum type) {
		this.type = type;
	}
	public TaskStatusEnum getStatus() {
		return status;
	}
	public void setStatus(TaskStatusEnum status) {
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
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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
	
	

}
