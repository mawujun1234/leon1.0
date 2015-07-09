package com.mawujun.store;

import javax.persistence.Transient;

public class BarcodeVO extends Barcode {
	
	private String subtype_name;
	
	private String prod_name;
	private String prod_style;
	
	private String brand_name;
	
	private String supplier_name;


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

	public String getProd_style() {
		return prod_style;
	}

	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}
}
