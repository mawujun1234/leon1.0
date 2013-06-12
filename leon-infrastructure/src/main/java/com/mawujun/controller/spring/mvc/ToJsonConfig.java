package com.mawujun.controller.spring.mvc;

import java.util.HashMap;
import java.util.Map;

public class ToJsonConfig {
	public transient Boolean autoWrap=true;//自动封装为某种格式
	
	public transient String filterPropertysName="filterPropertys";
	public transient String rootName="root";
	public transient String successName="success";
	public transient String totalName="total";
	public transient String startName="start";
	public transient String limitName="limit";
	public transient String pageNoName="page";
	
	public transient String filterPropertys=null;
	public transient Class[] filterClass=null;
	public transient Boolean enableHibernateLazyInitializerFilter=true;
	
	//关闭fastjson的循环引用处理
	public transient Boolean disableCircularReferenceDetect=true;
	public transient String datePattern="yyyy-MM-dd";
	
	//添加额外的属性
	public transient Map extProperties;
	
	
	
	public String getFilterPropertysName() {
		return filterPropertysName;
	}
	public void setFilterPropertysName(String filterPropertysName) {
		this.filterPropertysName = filterPropertysName;
	}
	public String getRootName() {
		return rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	public String getFilterPropertys() {
		return filterPropertys;
	}
	public void setFilterPropertys(String filterPropertys) {
		this.filterPropertys = filterPropertys;
	}
	public void setFilterPropertys(String filterPropertys,Class... clazz) {
		this.filterPropertys = filterPropertys;
		this.filterClass=clazz;
	}
	public Boolean getEnableHibernateLazyInitializerFilter() {
		return enableHibernateLazyInitializerFilter;
	}
	public void setEnableHibernateLazyInitializerFilter(
			Boolean enableHibernateLazyInitializerFilter) {
		this.enableHibernateLazyInitializerFilter = enableHibernateLazyInitializerFilter;
	}

	public String getSuccessName() {
		return successName;
	}
	public void setSuccessName(String successName) {
		this.successName = successName;
	}
	public String getTotalName() {
		return totalName;
	}
	public void setTotalName(String totalName) {
		this.totalName = totalName;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getLimitName() {
		return limitName;
	}
	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}
	public String getPageNoName() {
		return pageNoName;
	}
	public void setPageNoName(String pageNoName) {
		this.pageNoName = pageNoName;
	}
	public Boolean getAutoWrap() {
		return autoWrap;
	}
	public void setAutoWrap(Boolean autoWrap) {
		this.autoWrap = autoWrap;
	}
	public Boolean getDisableCircularReferenceDetect() {
		return disableCircularReferenceDetect;
	}
	public void setDisableCircularReferenceDetect(
			Boolean disableCircularReferenceDetect) {
		this.disableCircularReferenceDetect = disableCircularReferenceDetect;
	}
	public Class[] getFilterClass() {
		return filterClass;
	}
	public void setFilterClass(Class[] filterClass) {
		this.filterClass = filterClass;
	}
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	public Map getExtProperties() {
		return extProperties;
	}
	/**
	 * 判断是否有额外的属性
	 * @param key
	 * @param value
	 */
	public boolean hasExtProperty() {
		if(this.extProperties!=null && this.extProperties.size()>0){
			return true;
		} else {
			return false;
		}
	}
	public void addProperty(Object key,Object value) {
		if(this.extProperties==null) {
			this.extProperties=new HashMap();
		}
		this.extProperties.put(key, value);
	}
	
}
