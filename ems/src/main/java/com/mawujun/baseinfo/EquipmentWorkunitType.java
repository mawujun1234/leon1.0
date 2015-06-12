package com.mawujun.baseinfo;

/**
 * installout("领用"),borrow("借用"),task("任务回收");
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentWorkunitType {
	installout("领用"),borrow("借用"),task("任务回收");
	private String name;
	
	EquipmentWorkunitType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
