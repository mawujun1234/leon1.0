package com.mawujun.report;

import java.util.List;

public class InstallOutReport_subtype {
	private String subtype_id;
	private String subtype_name;//一级领用类型
	private List<InstallOutReport_prod> prodes;
	
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public List<InstallOutReport_prod> getProdes() {
		return prodes;
	}
	public void setProdes(List<InstallOutReport_prod> prodes) {
		this.prodes = prodes;
	}
}
