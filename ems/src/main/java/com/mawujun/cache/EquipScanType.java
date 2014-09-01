package com.mawujun.cache;

/**
 * 单据的类型，入库单，出库单等等
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipScanType {
	newInStore("新设备入库");
	
	private String name;
	EquipScanType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
