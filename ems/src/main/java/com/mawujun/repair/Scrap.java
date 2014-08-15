package com.mawujun.repair;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 报废单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_scrap")
public class Scrap implements IdEntity<String>{
	@Id
	@Column(length=18)
	private String id;
	@Column(length=25)
	private String ecode;
	@Column(length=500)
	private String reason;//报废原因
	@Column(length=500)
	private String residual;//残余值

	@Column(length=50)
	private String operater;//报废人
	private Date operateDate;//报废时间
	
	@Column(length=18)
	private String repair_id;//报废单对应的维修单

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getResidual() {
		return residual;
	}

	public void setResidual(String residual) {
		this.residual = residual;
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

	public String getRepair_id() {
		return repair_id;
	}

	public void setRepair_id(String repair_id) {
		this.repair_id = repair_id;
	}
}
