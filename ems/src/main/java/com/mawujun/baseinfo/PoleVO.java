package com.mawujun.baseinfo;

import java.util.List;



public class PoleVO extends Pole {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String area_name;
	private String customer_name;
	private String workunit_id;
	private String workunit_name;
	
	private Integer task_num;//任务数量
	
	private List<EquipmentVO> equipments;
	
//	private String task_id;//任务的id
//	private String task_type;
//	private String task_status;
//	private String task_memo;
//	
//	public String getTask_status_name(){
//		if(this.getTask_status()!=null && !"".equals(this.getTask_status())){
//			return TaskStatus.valueOf(this.getTask_status()).getName();
//		}
//		return null;
//	}
	
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
	public Integer getTask_num() {
		return task_num;
	}
	public void setTask_num(Integer task_num) {
		this.task_num = task_num;
	}
	public List<EquipmentVO> getEquipments() {
		return equipments;
	}
	public void setEquipments(List<EquipmentVO> equipments) {
		this.equipments = equipments;
	}

	
}
