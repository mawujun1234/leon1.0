package com.mawujun.adjust;


public class AdjustListVO extends AdjustList {
	
	
	private String brand_name;
	private String prod_name;
	private String prod_spec;
	private String subtype_name;
	private String supplier_name;
	private String prod_style;
	//private String equipment_status;
	
	public String getAdjustListStatus_name() {
		if(this.getAdjustListStatus()!=null){
			return this.getAdjustListStatus().getName();
		}
		return null;
	}
	
	
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getProd_spec() {
		return prod_spec;
	}
	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}


	public String getProd_style() {
		return prod_style;
	}


	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}


}
