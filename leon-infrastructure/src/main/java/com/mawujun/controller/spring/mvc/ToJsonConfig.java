package com.mawujun.controller.spring.mvc;

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
	public transient Boolean enableHibernateLazyInitializerFilter=true;
	public transient Boolean allowSingle=true;//判断是否允许
	
	
	
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
	public Boolean getEnableHibernateLazyInitializerFilter() {
		return enableHibernateLazyInitializerFilter;
	}
	public void setEnableHibernateLazyInitializerFilter(
			Boolean enableHibernateLazyInitializerFilter) {
		this.enableHibernateLazyInitializerFilter = enableHibernateLazyInitializerFilter;
	}
	public Boolean getAllowSingle() {
		return allowSingle;
	}
	public void setAllowSingle(Boolean allowSingle) {
		this.allowSingle = allowSingle;
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
	

}
