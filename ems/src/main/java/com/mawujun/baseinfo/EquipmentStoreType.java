package com.mawujun.baseinfo;

/**
 * 某个仓库入库的时候，是怎么入库的
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentStoreType {
	newinstore("新品入库"),installin("返库"),borrow("借用返库"),adjust("调拨入库"),repair("维修入库");
	private String name;
	
	EquipmentStoreType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	
}
