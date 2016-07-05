package com.mawujun.baseinfo;


public class AreaVO extends Area {
	private Boolean selected;

	private String workunit_id;
	
	private String workunit_name;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getWorkunit_id() {
		return workunit_id;
	}

	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}

	public String getWorkunit_name() {
		return workunit_name;
	}

	public void setWorkunit_name(String workunit_name) {
		this.workunit_name = workunit_name;
	}
}
