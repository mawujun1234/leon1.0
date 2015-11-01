package com.mawujun.mobile.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private TaskCreaterType createrType;//任务创建类型是中心端发布的 还是移动端自己发布的
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private TaskStatus status;//任务状态，submited状态的任务就算完成了，因为取消了任务确认的过程
	
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
	
	@Transient
	private String pole_code;
	
	private Date hitchDate;//故障时间，就是发现故障的时间
	private Date createDate;//创建时间
	private Date startHandDate;//开始处理时间，第一次保存的时候
	private Date submitDate;//提交时间
	//private Date approveDate;//管理人员审批时间
	private Date completeDate;//完成时间
	
	
	private Integer hitchType_id;//故障类型
	@Column(length=500)
	private String hitchType;//故障类型
	private Integer hitchReasonTpl_id;//故障原因，也用来存放维护内容
	@Column(length=500)
	private String hitchReason;//故障原因，也用来存放维护内容
	
	
	@Column(length=36)
	private String handleMethod_id;//处理方法
	@Column(length=100)
	private String handle_contact;//相关联系人的号码
	
	public Boolean getCanEdit(){
		if(this.getStatus()==TaskStatus.newTask || this.getStatus()==TaskStatus.handling || this.getStatus()==TaskStatus.read){
			return true;
		} else {
			return false;
		}
	}
	
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
	public Integer getHitchType_id() {
		return hitchType_id;
	}
	public void setHitchType_id(Integer hitchType_id) {
		this.hitchType_id = hitchType_id;
	}
	public Integer getHitchReasonTpl_id() {
		return hitchReasonTpl_id;
	}
	public void setHitchReasonTpl_id(Integer hitchReasonTpl_id) {
		this.hitchReasonTpl_id = hitchReasonTpl_id;
	}
	public TaskCreaterType getCreaterType() {
		return createrType;
	}
	public void setCreaterType(TaskCreaterType createrType) {
		this.createrType = createrType;
	}
	public String getPole_code() {
		return pole_code;
	}
	public void setPole_code(String pole_code) {
		this.pole_code = pole_code;
	}
	public Date getHitchDate() {
		return hitchDate;
	}
	public void setHitchDate(Date hitchDate) {
		this.hitchDate = hitchDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	public String getHandleMethod_id() {
		return handleMethod_id;
	}
	public void setHandleMethod_id(String handleMethod_id) {
		this.handleMethod_id = handleMethod_id;
	}
	public String getHandle_contact() {
		return handle_contact;
	}
	public void setHandle_contact(String handle_contact) {
		this.handle_contact = handle_contact;
	}
	
	

}
