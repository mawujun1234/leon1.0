package com.mawujun.baseinfo;

/**
 * 杆位的状态
 * ninstall("未安装"),installing("安装中"),using("使用中"),hitch("有损坏"),cancel("取消");
 * @author mawujun 16064988@qq.com  
 *
 */
public enum PoleStatus {
	uninstall("未安装"),installing("安装中"),using("使用中"),hitch("有损坏"),cancel("取消");
	private String name;
	PoleStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}

}
