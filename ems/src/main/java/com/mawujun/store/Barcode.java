package com.mawujun.store;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_barcode")
public class Barcode implements IdEntity<String>{
	@Id
	@Column(length=25)
	private String ecode;//条码，也可以说是某个订单明细下的所有条码
	@Column(length=36)
	private String orderlist_id;//订单明细的id
	@Column(length=8)
	private String ymd;//年月日，也可以说是批次
	
	//private Integer seqNum;//序号，就是从1开始，用于在导出条码的时候获取哪个范围的条码
	
	//用于判断某个订单本次条码生成的判断条件
	@Column(length=36)
	private String randomStr;
	
	private Date createDate;

	private Integer status=0;//0:未入库，1：已入库
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.ecode=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ecode;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderlist_id() {
		return orderlist_id;
	}
	public void setOrderlist_id(String orderlist_id) {
		this.orderlist_id = orderlist_id;
	}
	public String getRandomStr() {
		return randomStr;
	}
	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
