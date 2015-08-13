package com.mawujun.report;

import java.util.List;

/**
 * 前端设备汇总表
 * 这个类里面存放的是客户信息
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class FrontEquipSumReport {
	private String customer_id;
	private String customer_name;
	private List<FrontEquipSumReport_prod> prodes;
	
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public List<FrontEquipSumReport_prod> getProdes() {
		return prodes;
	}
	public void setProdes(List<FrontEquipSumReport_prod> prodes) {
		this.prodes = prodes;
	}
	

}
