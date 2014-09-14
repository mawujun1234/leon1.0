package com.mawujun.mobile.message;

/**
 * 消息类型
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum MessageType {
	overtime("超时提醒"),//消息提醒
	complete("任务结束");//任务结束的消息提醒
	
	private String name;
	MessageType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
}
