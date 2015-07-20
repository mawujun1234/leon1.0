package com.mawujun.mobile.task;

import javax.persistence.Transient;

public class TaskEquipmentListVO extends TaskEquipmentList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String subtype_name;
	@Transient
	private String prod_name;
	@Transient
	private String brand_name;
	@Transient
	private String supplier_name;
	
	private String style;
	
	public String getType_name() {
		if(this.getType()!=null){
			this.getType().getName();
		}
		return null;
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
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
}
