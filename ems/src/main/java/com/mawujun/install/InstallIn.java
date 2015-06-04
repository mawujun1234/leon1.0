package com.mawujun.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 设备返回 单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_installin")
public class InstallIn   implements IdEntity<String>{
	@Id
	@Column(length=15)
	private String id;//设备返回单号，年日日时分秒
	@Column(length=36)
	private String store_id;//返回仓库id
	private String operater;//仓管id
	private Date operateDate;//入库时间
	@Column(length=36)
	private String workUnit_id;//作业单位
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private InstallInType type;//好件入库 还是坏件入库
	@Column(length=100)
	private String memo;
	
	public String getType_name(){
		return type==null?null:type.getName();
	}
	
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
	public InstallInType getType() {
		return type;
	}
	public void setType(InstallInType type) {
		this.type = type;
	}

}
