package com.mawujun.adjust;

/**
 * 调拨类型，借用还是领用
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public enum AdjustType {
	installout("领用"),borrow("借用"),returnback("归还");
	
	private String name;
	AdjustType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
