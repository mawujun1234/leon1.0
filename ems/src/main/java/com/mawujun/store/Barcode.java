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
	
	@Column(length=2)
	private String type_id;//类型id
	@Column(length=4)
	private String subtype_id;//子类型id
	@Column(length=10)
	private String prod_id;//品名id
	@Column(length=15)
	private String brand_id;//品牌id
//	@Column(length=50)
//	private String style;//型号
	@Column(length=3)
	private String supplier_id;//供应商id,是订单指定的供应商
	@Column(length=36)
	private String store_id;//要入库的仓库id,是订单指定的入库仓库
	
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=true;//是否是新产品,如果订单是旧品订单，那就是旧的
	
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
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
//	public String getStyle() {
//		return style;
//	}
//	public void setStyle(String style) {
//		this.style = style;
//	}
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}

}
