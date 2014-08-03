package com.mawujun.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipment")
public class Equipment implements IdEntity<String>{
	@Id
	@Column(length=20)
	private String ecode;//条码 小类(2)+品名(2)+品牌(3)+供应商(3)+年月日(6)+流水号(3)=19位
//	@Column(length=2)
//	private String type_id;//类型id
	@Column(length=2)
	private String subtype_id;//子类型id
	@Column(length=2)
	private String prod_id;//品名id
	@Column(length=3)
	private String brand_id;//品牌id
	@Column(length=3)
	private String supplier_id;//供应商id
	@Column(length=50)
	private String style;//型号
	
	//private Date lastInDate;//最新一次入库时间
	@Column(precision=10,scale=2)
	private Double unitPrice;//价格
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isnew=true;//是否是新产品
	@Column(length=100)
	private String memo;

	//外键于表EquipmentStatus
	private Integer status=0;//1表示是在库
	@Column(updatable=false)
	private Date fisData;//first in stock date第一次入库时间
//	@Column(length=36)
//	private String store_id;//仓库id，所属仓库
	@Transient
	private Integer num;
	
	
//	@Transient
//	private Integer inStore_type;//在入库的时候用来接受web界面的数据的,入库单中使用
	@Transient
	private String subtype_name;
	@Transient
	private String prod_name;
	@Transient
	private String brand_name;
	@Transient
	private String supplier_name;
	@Transient
	private String store_id;
	@Transient
	private String store_name;
	@Transient
	private Boolean isInStore;//这个条码是否已经入过库了。是barcode中的条码的状态
	
	//=======入库的时候
	@Transient
	private String workUnit_id;
	@Transient
	private String workUnit_name;
//	@Transient
//	private Integer outStore_type;

	
	
//	private String ;
//	private String ;
//	private String ;
//	private String ;
	
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
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
//	public Boolean getIsInStore() {
//		return isInStore;
//	}
//	public void setIsInStore(Boolean isInStore) {
//		this.isInStore = isInStore;
//	}

	public Boolean getIsInStore() {
		return isInStore;
	}
	public void setIsInStore(Boolean isInStore) {
		this.isInStore = isInStore;
	}
	public String getWorkUnit_id() {
		return workUnit_id;
	}
	public void setWorkUnit_id(String workUnit_id) {
		this.workUnit_id = workUnit_id;
	}
	public String getWorkUnit_name() {
		return workUnit_name;
	}
	public void setWorkUnit_name(String workUnit_name) {
		this.workUnit_name = workUnit_name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
