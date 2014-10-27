package com.mawujun.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PatrolMonitor {
	private String id;
	private String name;
	private BigDecimal pole_nums;//杆位数
	
	private BigDecimal patrols;//已巡检杆位数
	//private BigDecimal patrols_rate;//完成率

	/**
	 * 未巡检的杆位
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public BigDecimal getUnpatrols() {
		if(this.getPatrols()==null || this.getPatrols().intValue()==0){
			return pole_nums;
		}
		return this.getPole_nums().subtract(this.getPatrols());
	}
	
	public String getPatrols_rate() {
		if(this.getPatrols()==null || this.getPatrols().intValue()==0){
			return "0%";
		}
		return this.getPatrols().divide(this.getPole_nums(),2,RoundingMode.HALF_UP).multiply(new BigDecimal(100))+"%";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPole_nums() {
		return pole_nums;
	}
	public void setPole_nums(BigDecimal pole_nums) {
		this.pole_nums = pole_nums;
	}
	public BigDecimal getPatrols() {
		return patrols;
	}
	public void setPatrols(BigDecimal patrols) {
		this.patrols = patrols;
	}
	
}
