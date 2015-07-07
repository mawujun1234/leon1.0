package com.mawujun.install;

public class InstallOutVO extends InstallOut {
	private String store_name;
	private String workUnit_name;
	private String project_name;
	private String operater_name;
	
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
	public String getWorkUnit_name() {
		return workUnit_name;
	}
	public void setWorkUnit_name(String workUnit_name) {
		this.workUnit_name = workUnit_name;
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
