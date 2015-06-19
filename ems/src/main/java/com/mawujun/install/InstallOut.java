package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 设备领用单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_installout")
public class InstallOut   implements IdEntity<String>{
	@Id
	@Column(length=15)
	private String id;//出库单号，年日日时分秒
	@Column(length=36)
	private String store_id;//出库仓库id
	private String operater;//仓管id
	private Date operateDate;//出库时间，领用日期
	@Column(length=36)
	private String workUnit_id;//作业单位
	
	@Column(length=36)
	private String project_id;//项目id
	
	//请求数量
	private Integer requestnum;
	

	@Column(length=100)
	private String memo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getWorkUnit_id() {
		return workUnit_id;
	}
	public void setWorkUnit_id(String workUnit_id) {
		this.workUnit_id = workUnit_id;
	}
	public Integer getRequestnum() {
		return requestnum;
	}
	public void setRequestnum(Integer requestnum) {
		this.requestnum = requestnum;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

}
