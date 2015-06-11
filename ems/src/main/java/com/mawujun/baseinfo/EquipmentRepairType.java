package com.mawujun.baseinfo;

/**
 * 维修中心的维修入库
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentRepairType {
	repair("维修入库");
	private String name;
	
	EquipmentRepairType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	
}
