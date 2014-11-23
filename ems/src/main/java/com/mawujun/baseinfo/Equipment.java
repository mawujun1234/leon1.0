package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipment")
public class Equipment implements IdEntity<String>{
	@Id
	@Column(length=25)
	private String ecode;
//	@Column(length=2)
//	private String type_id;//类型id
	@Column(length=2)
	private String subtype_id;//子类型id
	@Column(length=3)
	private String prod_id;//品名id
	@Column(length=15)
	private String brand_id;//品牌id
	@Column(length=3)
	private String supplier_id;//供应商id
	@Column(length=50)
	private String style;//型号
	@Column(length=36)
	private String order_id;//订单明细id
	
	//private Date lastInDate;//最新一次入库时间
	@Column(precision=10,scale=2)
	private Double unitPrice;//价格
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=true;//是否是新产品
	@Column(length=100)
	private String memo;

	//外键于表EquipmentStatus
	private Integer status=0;//1:表示是在库
	@Column(updatable=false)
	private Date fisData;//first in stock date第一次入库时间
	@Column(length=36)
	private String store_id;//仓库id，所属仓库
	@Column(length=36)
	private String workUnit_id;//作业单位id，所属的作业单位，和store_id，同时只能有一个有值
	@Column(length=36)
	private String pole_id;//杆位，所属的杆位，store_id，workUnit_id，pole_id三个职能存在一个
	



	
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
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getFisData() {
		return fisData;
	}
	public void setFisData(Date fisData) {
		this.fisData = fisData;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getWorkUnit_id() {
		return workUnit_id;
	}
	public void setWorkUnit_id(String workUnit_id) {
		this.workUnit_id = workUnit_id;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}

}
