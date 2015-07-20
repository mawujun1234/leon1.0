package com.mawujun.mobile.task;

/**
 * 任务的状态
 * submited状态的任务就算完成了，因为取消了任务确认的过程
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum TaskStatus {
	newTask("新任务"),read("已阅"),handling("处理中"),submited("已提交")
	,complete("完成");
	private String name;
	TaskStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
