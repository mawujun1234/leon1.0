package com.mawujun.mobile.task;

/**
 * 任务的状态
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum TaskStatusEnum {
	newTask("新任务"),read("已阅"),handling("处理中"),submited("已提交"),complete("完成");
	private String name;
	TaskStatusEnum(String name){
		this.name=name;
	}
}
