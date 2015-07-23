package com.mawujun.report;

import java.util.List;

public class InstallOutReport_type {
	private String type_id;
	private String type_name;//一级领用类型
	private List<InstallOutReport_subtype> subtypes;
	
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public List<InstallOutReport_subtype> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(List<InstallOutReport_subtype> subtypes) {
		this.subtypes = subtypes;
	}
	
}
