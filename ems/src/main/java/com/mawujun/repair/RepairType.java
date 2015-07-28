package com.mawujun.repair;

/**
 * 维修单的状态
 * @author mawujun 16064988@qq.com  
 *
 */
public enum RepairType {
	//to_repair(1,"发往维修中心"),repairing(2,"维修中"),back_store(3,"返库途中"),over(4,"完成"),scrap_confirm(5,"报废确认中"),scrap(6,"报废");
	innerrpa("内修"),outrpa("外修");

	private String name;
	private RepairType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
