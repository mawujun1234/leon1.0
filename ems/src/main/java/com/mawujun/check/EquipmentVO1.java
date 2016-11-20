package com.mawujun.check;

import com.mawujun.baseinfo.Equipment;

public class EquipmentVO1 extends Equipment {
private String subtype_name;
	
	private String prod_name;
	
	private String prod_spec;
	private Integer prod_quality_month;//质保
	
	private String brand_name;
	
	private String supplier_name;
	private String orginal_id;
	private String orginal_type;
	private String orginal_name;
	
	private Boolean diff=false;//是不是差异的条码
	
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
	public String getProd_spec() {
		return prod_spec;
	}
	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}
	public Integer getProd_quality_month() {
		return prod_quality_month;
	}
	public void setProd_quality_month(Integer prod_quality_month) {
		this.prod_quality_month = prod_quality_month;
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
	public String getOrginal_id() {
		return orginal_id;
	}
	public void setOrginal_id(String orginal_id) {
		this.orginal_id = orginal_id;
	}
	public String getOrginal_type() {
		return orginal_type;
	}
	public void setOrginal_type(String orginal_type) {
		this.orginal_type = orginal_type;
	}
	public String getOrginal_name() {
		return orginal_name;
	}
	public void setOrginal_name(String orginal_name) {
		this.orginal_name = orginal_name;
	}
	public Boolean getDiff() {
		return diff;
	}
	public void setDiff(Boolean diff) {
		this.diff = diff;
	}

}
