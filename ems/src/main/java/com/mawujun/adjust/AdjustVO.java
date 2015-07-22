package com.mawujun.adjust;

public class AdjustVO extends Adjust {
	private String str_in_name;
	private String str_out_name;
//	private String ecode;//用于在前台出库的时候
//
//	private String brand_name;
//	private String prod_name;
//	private String prod_spec;
//	private String subtype_name;
//	private String supplier_name;
//	private String equipment_style;
//	private String equipment_status;
	
	public String getAdjustType_name() {
		if(this.getAdjustType()!=null){
			return this.getAdjustType().getName();
		}
		return null;
	}
	public String getReturnStatus_name() {
		if(this.getReturnStatus()!=null){
			return this.getReturnStatus().getName();
		}
		return null;
	}
	public String getStatus_name() {
		if(this.getStatus()!=null){
			return this.getStatus().getName();
		}
		return null;
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
	
}
