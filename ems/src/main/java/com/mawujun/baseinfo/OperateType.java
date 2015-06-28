package com.mawujun.baseinfo;

/**
 * 用于设备生命周期中的操作类型
 * @author mawujun 16064988@qq.com  
 *
 */
public enum OperateType {

	newinstore("新品入库"),installout("领用"),borrow("借用"),installin("领用返库"),borrowreturn("借用返库"),adjust("调拨入库"),repair("维修入库"),scrap("报废"),
	task_install("安装"),task_cancel("卸载");
	private String name;
	
	OperateType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
}
