package com.mawujun.constant.cache;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.mawujun.utils.ConstantCacheHolder;

/**
 * 常数值，这个只是显示的，真正的常数值是CodeItem
 * Constant，ConstantType，CodeItem是真正的实体类，用于常数的增，删，改，查
 * BaseCode是直接从数据库中读取，或者从缓存中读取常数类的值
 * 其他常数类要继承这个类，例如ExampleType，并且把codeClassId设置的值和在新建这个常数类的id一样
 * @author mawujun
 *
 */
@MappedSuperclass
public abstract class ConstantItemProxy  {
	private String code;
	//CodeType类的id
	@Transient
	protected String constantCode;
	
	{
		setConstantCode();
	}
	
	public abstract void setConstantCode();

	//获取这个状态的名称是什么
	public String getName() {
		//1：缓存这块，还没有做好:2：常数类在前台展示的时候变成了Object的问题也没有解决，
		//this.typeClass+"===="+this.code是缓存的key
		if(ConstantCacheHolder.get(this.constantCode,this.code)==null){
			return null;
		}
		return ConstantCacheHolder.get(this.constantCode,this.code).getText();
	}
	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
