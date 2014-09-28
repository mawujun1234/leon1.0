package com.mawujun.baseinfo;

import javax.persistence.Transient;

public class EquipmentVO extends Equipment {
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
	@Transient
	private Integer num;
	@Transient
	private String pole_address;
	@Transient
	private String status_name;
	
	//=======入库的时候
	
	@Transient
	private String workUnit_name;
	
//	public String getStatus_name() {
//		if(this.getStatus()!=null){
//			EquipmentStatus.\.valueOf(this.getStatus())
//		}
////		
////		for (EquipmentStatus status : EquipmentStatus.values()) {
////			if (status.getValue() == this.getStatus()) {
////				return status.getName();
////			}
////		}
////		return null;
//	}
	
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
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public Boolean getIsInStore() {
		return isInStore;
	}
	public void setIsInStore(Boolean isInStore) {
		this.isInStore = isInStore;
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
	public String getPole_address() {
		return pole_address;
	}
	public void setPole_address(String pole_address) {
		this.pole_address = pole_address;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}
