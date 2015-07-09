package com.mawujun.store;

import javax.persistence.Transient;

import com.mawujun.baseinfo.EquipmentProdType;

public class OrderListVO extends OrderList {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type_name;
	private String subtype_name;
	private String prod_id;
	private String prod_style;
	private String prod_name;
	private String prod_spec;
	private String prod_unit;
	private String prod_memo;
	private EquipmentProdType prod_type;
	@Transient
	private String brand_name;
//	@Transient
//	private String supplier_name;
	@Transient
	private String store_name;
	
	private Integer printNum=0;//要打印的数量
	
	private Boolean exportStatus=false;//有
	
	private Boolean noedit=false;//该订单明细不可编辑
	
	public boolean getLeaf() {
		if(prod_type==EquipmentProdType.TJ){
			return false;
		} else {
			return true;
		}
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

	public String getProd_spec() {
		return prod_spec;
	}

	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}

	public String getProd_unit() {
		return prod_unit;
	}

	public void setProd_unit(String prod_unit) {
		this.prod_unit = prod_unit;
	}
	public String getProd_memo() {
		return prod_memo;
	}
	public void setProd_memo(String prod_memo) {
		this.prod_memo = prod_memo;
	}



	public String getProd_id() {
		return prod_id;
	}



	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}



	public Boolean getNoedit() {
		return noedit;
	}



	public void setNoedit(Boolean noedit) {
		this.noedit = noedit;
	}



	public String getType_name() {
		return type_name;
	}



	public void setType_name(String type_name) {
		this.type_name = type_name;
	}



	public String getProd_style() {
		return prod_style;
	}



	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}



	public EquipmentProdType getProd_type() {
		return prod_type;
	}



	public void setProd_type(EquipmentProdType prod_type) {
		this.prod_type = prod_type;
	}
	
}
