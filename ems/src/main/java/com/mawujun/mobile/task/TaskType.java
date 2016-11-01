package com.mawujun.mobile.task;
/**
 * 任务类型
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum TaskType {
	newInstall("新安装"),repair("维修维护"),patrol("巡检"),cancel("取消")
	,check("盘点");
	
	private String name;
	
	TaskType(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
