package com.mawujun.repair;


public class RepairVO extends Repair {
	private String rpa_name;
	private String str_in_name;
	private String str_out_name;
//	private String rpa_type_name;
//	private String status_name;
	
	
	private String brand_name;
	private String prod_name;
	private String subtype_name;
	private String supplier_name;
	private String equipment_style;
	private String equipment_status;
	
	public String getRpa_type_name() {
		if("innerrpa".equals(this.getRpa_type())){
			return "维修";
		} else if("outrpa".equals(this.getRpa_type())){
			return "外修";
		} else {
			return null;
		}
	}
	public String getStatus_name() {
		for(RepairStatus repairstatus:RepairStatus.values()) {
			if(repairstatus.getValue()==this.getStatus()){
				return repairstatus.getName();
			}
		}
		return null;
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
	public String getEquipment_status() {
		return equipment_status;
	}
	public void setEquipment_status(String equipment_status) {
		this.equipment_status = equipment_status;
	}
	public String getEquipment_style() {
		return equipment_style;
	}
	public void setEquipment_style(String equipment_style) {
		this.equipment_style = equipment_style;
	}
	
}
