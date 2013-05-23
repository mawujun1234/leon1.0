package com.mawujun.controller.spring.mvc;

import org.springframework.ui.ModelMap;

/**
 * 用于前后台传递数据的
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
public class ResultMap extends ModelMap {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private transient String filterPropertys;
//	private transient boolean enableHibernateLazyInitializerFilter=true;
//	private transient boolean allowSingle=true;
//	private transient boolean success=true;
//	private Object root;
	
	public static final String filterPropertysName="filterPropertys";
	public static final String enableHibernateLazyInitializerFilter="enableHibernateLazyInitializerFilter";
	public static final String allowSingle="allowSingle";
	public static final String root="root";

	{
		this.put("filterPropertys", "");
		this.put("enableHibernateLazyInitializerFilter", true);
		this.put("allowSingle", true);
		this.put("success", true);
		this.put("root", null);
	}
	
	
	public String getFilterPropertys() {
		return (String)this.get("filterPropertys");
	}
	/**
	 * 启用属性过滤，参数的形式为  “name,age,height”,这样就表示过滤掉name,age,height
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param filterPropertys
	 */
	public void setFilterPropertys(String filterPropertys) {
		this.put("filterPropertys", filterPropertys);
	}
	
	public Boolean isEnableHibernateLazyInitializerFilter() {
		return (Boolean)this.get("enableHibernateLazyInitializerFilter");
	}
	/**
	 * 是否过滤掉延迟加载的属性
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param enableHibernateLazyInitializerFilter
	 */
	public void setEnableHibernateLazyInitializerFilter(
			boolean enableHibernateLazyInitializerFilter) {
		//this.enableHibernateLazyInitializerFilter = enableHibernateLazyInitializerFilter;
		this.put("enableHibernateLazyInitializerFilter", enableHibernateLazyInitializerFilter);
	}
	public boolean isAllowSingle() {
		return (Boolean)this.get("allowSingle");
	}
	/**
	 * 如果设置为false，表示返回的数据都是以数组的形式返回
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param allowSingle
	 */
	public void setAllowSingle(boolean allowSingle) {
		//this.allowSingle = allowSingle;
		this.put("allowSingle", allowSingle);
		
	}
	public boolean isSuccess() {
		return (Boolean)this.get("success");
	}
	public void setSuccess(boolean success) {
		this.put("success", success);
	}
	public Object getRoot() {
		return this.get("root");
	}
	public void setRoot(Object root) {
		this.put("root", root);
	}
	public void setTotalProperty(int totalProperty) {
		this.put("totalProperty", totalProperty);
	}
	public int getTotalProperty() {
		if(this.get("totalProperty")==null){
			return 0;
		} else {
			return (Integer)this.get("totalProperty");
		}
		
	}

	

}
