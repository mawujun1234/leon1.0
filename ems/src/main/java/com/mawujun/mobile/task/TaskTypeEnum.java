package com.mawujun.mobile.task;
/**
 * 任务类型
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum TaskTypeEnum {
	newInstall("新安装"),repair("维修/维护"),patrol("巡检");
	
	private String name;
	
	TaskTypeEnum(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
