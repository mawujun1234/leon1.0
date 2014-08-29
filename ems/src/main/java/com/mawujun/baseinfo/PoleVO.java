package com.mawujun.baseinfo;

import com.mawujun.mobile.task.TaskStatus;


public class PoleVO extends Pole {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String area_name;
	private String customer_name;
	private String workunit_id;
	private String workunit_name;
	
	private String task_id;//任务的id
	private String task_type;
	private String task_status;
	private String task_memo;
	
	public String getTask_status_name(){
		if(this.getTask_status()!=null && !"".equals(this.getTask_status())){
			return TaskStatus.valueOf(this.getTask_status()).getName();
		}
		return null;
	}
	
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getTask_memo() {
		return task_memo;
	}
	public void setTask_memo(String task_memo) {
		this.task_memo = task_memo;
	}
	
}
