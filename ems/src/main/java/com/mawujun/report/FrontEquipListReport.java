package com.mawujun.report;

import java.util.List;

/**
 * 前端设备汇总表
 * 这个类里面存放的是客户信息
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class FrontEquipListReport {
	private String pole_id;
	private String pole_name;
	private String pole_code;
	private List<FrontEquipListReport_prod> prodes;
	
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	public String getPole_name() {
		return pole_name;
	}
	public void setPole_name(String pole_name) {
		this.pole_name = pole_name;
	}
	public String getPole_code() {
		return pole_code;
	}
	public void setPole_code(String pole_code) {
		this.pole_code = pole_code;
	}
	public List<FrontEquipListReport_prod> getProdes() {
		return prodes;
	}
	public void setProdes(List<FrontEquipListReport_prod> prodes) {
		this.prodes = prodes;
	}
	
	
	

}
