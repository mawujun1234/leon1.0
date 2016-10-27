package com.mawujun.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InstallOutReport_prod {
	private String prod_id;
	private String store_id;
	private String installouttype_name;//一级领用类型
	private String installouttype_content;//二级领用类型
//	private String type_name;
//	private String subtype_name;
	private String brand_name;
	private String prod_name;
	private String prod_style;
	private String prod_unit;
	
	private Integer installoutnum;
	private BigDecimal value_net;
	
	
	
	public BigDecimal getValue_net() {
		if(value_net!=null){
			return value_net.setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}
	public void setValue_net(BigDecimal value_net) {
		this.value_net = value_net;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getInstallouttype_name() {
		return installouttype_name;
	}
	public void setInstallouttype_name(String installouttype_name) {
		this.installouttype_name = installouttype_name;
	}
	public String getInstallouttype_content() {
		return installouttype_content;
	}
	public void setInstallouttype_content(String installouttype_content) {
		this.installouttype_content = installouttype_content;
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
	public String getProd_style() {
		return prod_style;
	}
	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}
	public String getProd_unit() {
		return prod_unit;
	}
	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}
	public Integer getInstalloutnum() {
		return installoutnum;
	}
	public void setInstalloutnum(Integer installoutnum) {
		this.installoutnum = installoutnum;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
}
