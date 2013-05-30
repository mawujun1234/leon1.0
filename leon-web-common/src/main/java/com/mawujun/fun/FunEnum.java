package com.mawujun.fun;

/**
 * 功能分为两种，一种是模块，一种功能
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
public enum FunEnum {
	module("模块"),fun("功能"); 
	
	private String text;
	
	public String getText() {
		return text;
	}

	private FunEnum(String t)
	{
		this.text=t;
	}
	
//	@Override
//	public String toString() {
//		return text;
//	}
}
