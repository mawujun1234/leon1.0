package com.mawujun.repair;

/**
 * 维修单的状态
 * @author mawujun 16064988@qq.com  
 *
 */
public enum RepairStatus {
	One(1,"发往维修中心"),Two(2,"维修中"),Three(3,"返库途中"),Four(4,"完成"),Five(5,"报废确认中"),Six(6,"报废");
	
	private Integer value;
	private String name;
	private RepairStatus(Integer value,String name){
		this.value=value;
		this.name=name;
	}
	public Integer getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
}
