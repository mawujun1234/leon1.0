package com.mawujun.mobile.task;

/**
 * 当设备被锁定的时候，是被什么锁定的
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum LockType {
	task("任务");
	private String name;
	LockType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
