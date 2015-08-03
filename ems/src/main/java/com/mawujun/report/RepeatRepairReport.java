package com.mawujun.report;

public class RepeatRepairReport {
	private String prod_name;//设备名称
	private String ecode;
	private String str_out_id;
	private String str_out_name;//出库仓库
	
	private String rpa_user_id;//维修人员
	private String rpa_user_name;//维修人员
	
	private String rpa_in_date;//维修入库
	private String rpa_out_date;//维修出库
	private String str_in_date;//入库日期
	
	private Integer repeate_count;
	
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getStr_out_id() {
		return str_out_id;
	}
	public void setStr_out_id(String str_out_id) {
		this.str_out_id = str_out_id;
	}
	public String getStr_out_name() {
		return str_out_name;
	}
	public void setStr_out_name(String str_out_name) {
		this.str_out_name = str_out_name;
	}
	public String getRpa_in_date() {
		return rpa_in_date;
	}
	public void setRpa_in_date(String rpa_in_date) {
		this.rpa_in_date = rpa_in_date;
	}
	public String getRpa_user_id() {
		return rpa_user_id;
	}
	public void setRpa_user_id(String rpa_user_id) {
		this.rpa_user_id = rpa_user_id;
	}
	public String getRpa_out_date() {
		return rpa_out_date;
	}
	public void setRpa_out_date(String rpa_out_date) {
		this.rpa_out_date = rpa_out_date;
	}
	public String getStr_in_date() {
		return str_in_date;
	}
	public void setStr_in_date(String str_in_date) {
		this.str_in_date = str_in_date;
	}
	public String getRpa_user_name() {
		return rpa_user_name;
	}
	public void setRpa_user_name(String rpa_user_name) {
		this.rpa_user_name = rpa_user_name;
	}
	public Integer getRepeate_count() {
		return repeate_count;
	}
	public void setRepeate_count(Integer repeate_count) {
		this.repeate_count = repeate_count;
	}
	
}
