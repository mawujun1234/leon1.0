package com.mawujun.mobile.check;

/**
 * 盘点单状态
 * 
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum CheckStatus {
	handling("处理中"),complete("完成");
	private String name;
	CheckStatus(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
}
