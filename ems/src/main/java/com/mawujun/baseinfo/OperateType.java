package com.mawujun.baseinfo;

/**
 * 用于设备生命周期中的操作类型
 * @author mawujun 16064988@qq.com  
 *
 */
public enum OperateType {

	newinstore("新品入库")
	,install_out("领用"),install_in("领用返库")
	,borrow_out("借用"),borrow_return("借用返库")
	,adjust_in("调拨入库"),adjust_out("调拨出库")
	,repair_store_out("维修-仓库出库"),repair_in("维修-维修中心入库"),repair_out("维修-维修中心出库"),repair_store_in("维修-维修好后仓库入库")
	,scrap("报废"),
	task_install("安装"),task_cancel("卸载")
	,manual_wait_for_repair("手工设置待维修"),manual_to_old("手工设置为旧品");
	private String name;
	
	OperateType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
}
