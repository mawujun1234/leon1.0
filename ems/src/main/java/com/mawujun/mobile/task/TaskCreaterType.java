package com.mawujun.mobile.task;

/**
 * 任务的创建者类型
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum TaskCreaterType {
	manager("管理人员"),workunit("作业单位");
	
	private String name;
	TaskCreaterType(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
}
