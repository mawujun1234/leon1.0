package com.mawujun.baseinfo;

import javax.persistence.Transient;

public class EquipmentProdVO extends EquipmentProd {
	@Transient
	private String brand_name;
	@Transient
	private String subtype_name;
	@Transient
	private String ecode_num;//品名下的ecode的数量，或者是
	
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getEcode_num() {
		return ecode_num;
	}
	public void setEcode_num(String ecode_num) {
		this.ecode_num = ecode_num;
	}
}
