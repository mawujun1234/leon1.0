package com.mawujun.repair;

import java.util.Date;

import com.mawujun.baseinfo.EquipmentStatus;


public class RepairVO extends Repair {
	private String rpa_name;//维修中心名称
	private String str_in_name;
	private String str_out_name;
//	private String rpa_type_name;
//	private String status_name;
	private String rpa_user_name;//维修人名称
	
	private String workunit_name;
	
	
	private String brand_name;
	private String prod_name;
	private String prod_spec;
	private String subtype_name;
	private String supplier_name;
	private String equipment_style;
	private EquipmentStatus equipment_status;
	
	private String scrap_id;//报废单的单号
	private String scrap_reason;//报废原因
	private String scrap_residual;//报废残值
	private Date scrap_operateDate;//报废确认时间
	
	public String getRpa_type_name() {
		if(this.getRpa_type()!=null){
			return this.getRpa_type().getName();
		} else {
			return null;
		}
		
	}
	public String getStatus_name() {
		if(this.getStatus()!=null){
			return this.getStatus().getName();
		} else {
			return null;
		}
		
	}
	public String getEquipment_status_name() {
		if(equipment_status==null){
			return "";
		}
		return equipment_status.getName();
		
	}

	
	public String getRpa_name() {
		return rpa_name;
	}
	public void setRpa_name(String rpa_name) {
		this.rpa_name = rpa_name;
	}
	public String getStr_in_name() {
		return str_in_name;
	}
	public void setStr_in_name(String str_in_name) {
		this.str_in_name = str_in_name;
	}
	public String getStr_out_name() {
		return str_out_name;
	}
	public void setStr_out_name(String str_out_name) {
		this.str_out_name = str_out_name;
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

	public String getEquipment_style() {
		return equipment_style;
	}
	public void setEquipment_style(String equipment_style) {
		this.equipment_style = equipment_style;
	}
	public String getScrap_id() {
		return scrap_id;
	}
	public void setScrap_id(String scrap_id) {
		this.scrap_id = scrap_id;
	}


	public String getWorkunit_name() {
		return workunit_name;
	}


	public void setWorkunit_name(String workunit_name) {
		this.workunit_name = workunit_name;
	}


	public String getProd_spec() {
		return prod_spec;
	}


	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}


	public String getRpa_user_name() {
		return rpa_user_name;
	}


	public void setRpa_user_name(String rpa_user_name) {
		this.rpa_user_name = rpa_user_name;
	}


	public String getScrap_reason() {
		return scrap_reason;
	}


	public void setScrap_reason(String scrap_reason) {
		this.scrap_reason = scrap_reason;
	}


	public String getScrap_residual() {
		return scrap_residual;
	}


	public void setScrap_residual(String scrap_residual) {
		this.scrap_residual = scrap_residual;
	}


	public Date getScrap_operateDate() {
		return scrap_operateDate;
	}


	public void setScrap_operateDate(Date scrap_operateDate) {
		this.scrap_operateDate = scrap_operateDate;
	}
	public EquipmentStatus getEquipment_status() {
		return equipment_status;
	}
	public void setEquipment_status(EquipmentStatus equipment_status) {
		this.equipment_status = equipment_status;
	}
	
}
