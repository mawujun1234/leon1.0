package com.mawujun.baseinfo;

public enum EquipmentWorkunitType {
	installout("领用"),borrow("借用"),task("任务入库");
	private String name;
	
	EquipmentWorkunitType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
