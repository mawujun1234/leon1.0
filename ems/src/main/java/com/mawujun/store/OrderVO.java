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
	private String project_name;
	
	private String operater_name;
	
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

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getOperater_name() {
		return operater_name;
	}

	public void setOperater_name(String operater_name) {
		this.operater_name = operater_name;
	}
	
	
	
}
