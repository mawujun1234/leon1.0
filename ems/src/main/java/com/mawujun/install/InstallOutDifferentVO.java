package com.mawujun.install;

import java.util.Date;

public class InstallOutDifferentVO extends InstallOutList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subtype_name;
	private String prod_name;
	private String prod_spec;
	private String prod_unit;
	private String brand_name;
	private String supplier_name;
	private String prod_style;
	
	private String type;//这个单据是借用单还是领用单;
//	private String installouttype_content;
	private Date operateDate;
	
	
	private String installOutType_name;
	
	public String getInstallOutListType_name(){
		if(this.getInstallOutListType()!=null){
			return this.getInstallOutListType().getName();
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
	public String getProd_spec() {
		return prod_spec;
	}
	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
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

	public String getInstallOutType_name() {
		return installOutType_name;
	}
	public void setInstallOutType_name(String installOutType_name) {
		this.installOutType_name = installOutType_name;
	}

	public String getProd_unit() {
		return prod_unit;
	}

	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProd_style() {
		return prod_style;
	}

	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}


}
