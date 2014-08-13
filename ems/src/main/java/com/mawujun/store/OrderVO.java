package com.mawujun.store;

import javax.persistence.Transient;

public class OrderVO extends Order {
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
	@Transient
	private String store_name;
	
	private Integer printNum=0;//要打印的数量
	
	private Boolean exportStatus=false;//有
	
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
	public Integer getPrintNum() {
		return printNum;
	}
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
	public Boolean getExportStatus() {
		return exportStatus;
	}
	public void setExportStatus(Boolean exportStatus) {
		this.exportStatus = exportStatus;
	}
	
}
