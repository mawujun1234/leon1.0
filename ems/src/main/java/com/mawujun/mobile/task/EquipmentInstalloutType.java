package com.mawujun.mobile.task;
/**
 * 在设备扫描的的时候，判断设备是借出来的还领出来的
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentInstalloutType {
	installout("领"),borrow("借"),other("");
	
	private String name;
	EquipmentInstalloutType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
