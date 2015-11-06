package com.mawujun.baseinfo;

/**
 * 设备所在的位置，仓库，维修中心，杆位，作业单位<br/>
 * store("仓库"),repair("维修中心"),pole("点位"),workunit("作业单位"
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum EquipmentPlace {
	store("仓库"),repair("维修中心"),pole("点位"),workunit("作业单位"),scrap("已报废");
	private String name;
	
	EquipmentPlace(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
