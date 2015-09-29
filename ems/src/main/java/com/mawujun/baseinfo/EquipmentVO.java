package com.mawujun.baseinfo;


public class EquipmentVO extends Equipment {
	
	private String subtype_name;
	
	private String prod_name;
	
	private String prod_spec;
	private Integer prod_quality_month;//质保
	
	private String brand_name;
	
	private String supplier_name;
	
	private String store_id;
	
	private String store_name;
	
	private Boolean isInStore;//这个条码是否已经入过库了。是barcode中的条码的状态
	
	private Integer num;
	
	private String pole_code;
	private String pole_address;
	//
	//private String status_name;
	
	//=======入库的时候
	private String workUnit_id;
	
	private String workUnit_name;
	
	public String getStatus_name() {
//		for (EquipmentStatus status : EquipmentStatus.values()) {
//			if (status.getValue() == this.getStatus()) {
//				return status.getName();
//			}
//		}
//		return null;
		return this.getStatus()==null?null:this.getStatus().getName();
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

	public String getProd_spec() {
		return prod_spec;
	}

	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}

	public String getWorkUnit_id() {
		return workUnit_id;
	}

	public void setWorkUnit_id(String workUnit_id) {
		this.workUnit_id = workUnit_id;
	}

	public Integer getProd_quality_month() {
		return prod_quality_month;
	}

	public void setProd_quality_month(Integer prod_quality_month) {
		this.prod_quality_month = prod_quality_month;
	}

	public String getPole_code() {
		return pole_code;
	}

	public void setPole_code(String pole_code) {
		this.pole_code = pole_code;
	}
}
