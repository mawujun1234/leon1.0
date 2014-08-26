package com.mawujun.baseinfo;

/**
 * 杆位的状态
 * @author mawujun 16064988@qq.com  
 *
 */
public enum PoleStatus {
	uninstall("未安装"),installing("安装中"),using("使用中"),cancel("取消");
	private String name;
	PoleStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
