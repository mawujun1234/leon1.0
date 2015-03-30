package com.mawujun.repair;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 报废单,一张维修单，只有一张报废单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="ems_scrap",uniqueConstraints=@UniqueConstraint(columnNames={"repair_id"}))
public class Scrap implements IdEntity<String>{
	@Id
	@Column(length=18)
	private String id;
	@Column(length=25)
	private String ecode;
	@Column(length=500)
	private String reason;//报废原因
//	@Column(length=500)
//	private String residual;//残余值
	//@Column(length=500)
	private Double residual;//残余值

	@Column(length=50,updatable=false)
	private String scrpReqOper;//报废申请人
	@Column(updatable=false)
	private Date scrpReqDate;//报废申请时间
	
	@Column(length=50)
	private String operater;//报废确认人
	private Date operateDate;//报废确认时间
	
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

	public String getScrpReqOper() {
		return scrpReqOper;
	}

	public void setScrpReqOper(String scrpReqOper) {
		this.scrpReqOper = scrpReqOper;
	}

	public Date getScrpReqDate() {
		return scrpReqDate;
	}

	public void setScrpReqDate(Date scrpReqDate) {
		this.scrpReqDate = scrpReqDate;
	}

	public Double getResidual() {
		return residual;
	}

	public void setResidual(Double residual) {
		this.residual = residual;
	}
}
