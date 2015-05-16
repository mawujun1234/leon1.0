package com.mawujun.store;

import javax.persistence.Transient;

public class OrderVO extends Order {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	@Transient
//	private String subtype_name;
//	@Transient
//	private String prod_name;
//	private String prod_spec;
//	private String prod_unit;
//	@Transient
//	private String brand_name;
//	@Transient
//	private String supplier_name;
	@Transient
	private String store_name;
	
//	private Integer printNum=0;//要打印的数量
	
//	private Boolean exportStatus=false;//有
	
	public String getStatus_name() {
		if(this.getStatus()!=null){
			return this.getStatus().getName();
		}
		return null;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	
	
}
