package com.mawujun.constant.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.mawujun.constant.cache.CodeCacheHolder;

/**
 * 常数值，这个只是显示的，真正的常数值是CodeItem
 * CodeType，CodeCategory，CodeItem是真正的实体类，用于常数的增，删，改，查
 * BaseCode是直接从数据库中读取，或者从缓存中读取常数类的值
 * 其他常数类要继承这个类，例如ExampleType，并且把codeClassId设置的值和在新建这个常数类的id一样
 * @author mawujun
 *
 */
@MappedSuperclass
public abstract class CodeItemProxy  {
	private String code;
	//CodeType类的id
	@Transient
	protected String codeTypeId;
	
	{
		setCodeTypeId();
	}
	
	public abstract void setCodeTypeId();

	//获取这个状态的名称是什么
	public String getName() {
		//this.typeClass+"===="+this.code是缓存的key
		if(CodeCacheHolder.get(this.codeTypeId,this.code)==null){
			return null;
		}
		return CodeCacheHolder.get(this.codeTypeId,this.code).getName();
	}
	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
