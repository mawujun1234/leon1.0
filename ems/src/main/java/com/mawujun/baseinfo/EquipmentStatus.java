package com.mawujun.baseinfo;

/**
	no_storage(0,"未入库"),
	in_storage(1,"已入库"),
	out_storage(2,"施工持有"),//安装出库 改成了施工持有，在从仓库领出和从点位上拆下后，都变成这个状态
	using(3,"使用中"),
	//breakdown(4,"损坏"),
	wait_for_repair(5,"入库待维修"),
	to_repair(6,"发往维修中心"),
	outside_repairing(7,"外修中"),
	inner_repairing(8,"维修中"),
	out_repair(9,"维修后已出库"),
	in_transit(10,"在途"),
	scrapped(30,"报废");
 * 
 * 
 * 30：报废
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum EquipmentStatus {
	no_storage(0,"未入库"),
	in_storage(1,"已入库"),
	out_storage(2,"施工持有"),//安装出库 改成了施工持有，在从仓库领出和从点位上拆下后，都变成这个状态
	using(3,"使用中"),
	//breakdown(4,"损坏"),
	wait_for_repair(5,"入库待维修"),
	to_repair(6,"发往维修中心"),
	outside_repairing(7,"外修中"),
	inner_repairing(8,"维修中"),
	out_repair(9,"维修后已出库"),
	in_transit(10,"在途"),
	scrapped(30,"报废");
	
	private int value;
	private String name;
	EquipmentStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
}
