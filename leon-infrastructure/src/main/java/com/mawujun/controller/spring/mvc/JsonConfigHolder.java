package com.mawujun.controller.spring.mvc;

import java.util.HashMap;
import java.util.Map;


/**
 * 在前台往后台返回数据的时候，的配置文件,
 * 在使用的时候一定要建立一个Filter对JsonConfigHolder进行清理，即调用JsonConfigHolder.remove()方法，
 * 因为很多web服务器都使用了线程池，线程使用后是不会销毁的，这样的话，这个对象就会被别的线程使用了，就会存在冲突。
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
public class JsonConfigHolder {
	//private static Logger logger = LogManager.getLogger(JsonConfigHolder.class);  
	
	protected static ThreadLocal<ToJsonConfig> threadLocal = new ThreadLocal<ToJsonConfig>(){
		protected ToJsonConfig initialValue() {
			return new ToJsonConfig();
			
		}
	};

	public static ThreadLocal<ToJsonConfig> getThreadLocal(){
		return threadLocal;
	}
	
	public static void reset(){
		threadLocal.remove();
		threadLocal.set(new ToJsonConfig());
	}
	public static void remove() {
		threadLocal.remove();
	}
	public static String getFilterPropertysName() {
		
		return threadLocal.get().getFilterPropertysName();
	}


	public static void setFilterPropertysName(String filterPropertysName) {
		
		threadLocal.get().setFilterPropertysName(filterPropertysName);
	}
	
	public static String getFilterPropertys() {
		
		return threadLocal.get().getFilterPropertys();
	}
	/**
	 * 制定为哪些类进行属性过滤 JsonConfigHolder.setFilterPropertys("parent,children")
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param filterPropertys
	 */
	public static void setFilterPropertys(String filterPropertys) {
		
		threadLocal.get().setFilterPropertys(filterPropertys);
	}
	/**
	 * 被过滤属性所在的类
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @return
	 */
	public static Class[] getFilterClass() {
		return threadLocal.get().getFilterClass();
	}
	public static void setFilterClass(Class... filterClass) {
		threadLocal.get().setFilterClass(filterClass);
	}
	/**
	 * 制定为哪些类进行属性过滤 JsonConfigHolder.setFilterPropertys("parent,children",MenuItem.class,Fun.class);
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @param filterPropertys 例如:"name,age"
	 * @param clazz
	 */
	public static void setFilterPropertys(String filterPropertys,Class... clazz) {		
		threadLocal.get().setFilterPropertys(filterPropertys,clazz);
	}


	public static Boolean getEnableHibernateLazyInitializerFilter() {
		
		return  threadLocal.get().getEnableHibernateLazyInitializerFilter();
	}


	public static void setEnableHibernateLazyInitializerFilterName(
			Boolean enableHibernateLazyInitializerFilter) {
		
		threadLocal.get().setEnableHibernateLazyInitializerFilter(enableHibernateLazyInitializerFilter);
	}

	public static String getRootName() {
		
		return threadLocal.get().getRootName();
	}


	public static void setRootName(String rooName) {
		
		threadLocal.get().setRootName(rooName);
	} 
	
	public static String getSuccessName() {
		
		return threadLocal.get().getSuccessName();
	}
	public static void setSuccessName(String successName) {
		
		threadLocal.get().setSuccessName(successName);
	}
	
	public static boolean getSuccessValue() {
		return threadLocal.get().getSuccessValue();
	}
	public static void setSuccessValue(boolean successValue) {
		threadLocal.get().setSuccessValue(successValue);
	}
	
	public static String getTotalName() {
		
		return threadLocal.get().getTotalName();
	}
	public static void setTotalName(String totalName) {
		
		threadLocal.get().setTotalName(totalName);
	}
	public static String getStartName() {
		
		return threadLocal.get().getStartName();
	}
	public static void setStartName(String startName) {
		
		threadLocal.get().setStartName(startName);
	}
	
	public static String getLimitName() {
		
		return threadLocal.get().getLimitName();
	}
	public static void setLimitName(String limitName) {
		
		threadLocal.get().setLimitName(limitName);
	}
	public static String getPageNoName() {
		
		return threadLocal.get().getPageNoName();
	}
	public static void setPageNoName(String pageNoName) {
		
		threadLocal.get().setPageNoName(pageNoName);
	}
	/**
	 * 是否自动封装为extjs 默认的数据传输格式
	 * @author mawujun email:mawujun1234@163.com qq:16064988
	 * @return
	 */
	public static Boolean getAutoWrap() {
		
		return threadLocal.get().getAutoWrap();
	}
	public static void setAutoWrap(Boolean autoWrap) {
		
		threadLocal.get().setAutoWrap(autoWrap);
	}
	
	public static String getDatePattern() {
		return threadLocal.get().getDatePattern();
	}
	/**
	 * 设置日期的格式，默认是yyyy-MM-dd
	 * @param datePattern
	 */
	public static void setDatePattern(String datePattern) {
		threadLocal.get().setDatePattern(datePattern);
	}
	
	public static Boolean getDisableCircularReferenceDetect() {
		
		return threadLocal.get().getDisableCircularReferenceDetect();
	}
	public static void setDisableCircularReferenceDetect(
			Boolean disableCircularReferenceDetect) {
		
		threadLocal.get().setDisableCircularReferenceDetect(disableCircularReferenceDetect);
	}
	
	public static Map getExtProperties() {
		return threadLocal.get().getExtProperties();
	}
	/**
	 * 判断是否有额外的属性
	 * @param key
	 * @param value
	 */
	public static boolean hasExtProperty() {
		return threadLocal.get().hasExtProperty();
	}
	/**
	 * 添加额外的属性上传到界面上去
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param key
	 * @param value
	 */
	public static void addProperty(Object key,Object value) {
		threadLocal.get().addProperty(key, value);
	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public static Boolean getWriteMapNullValue() {
		return threadLocal.get().getWriteMapNullValue();
	}
	/**
	 * 是否要把null转换，false：不转换，true转换
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param writeMapNullValue
	 */
	public static void setWriteMapNullValue(Boolean writeMapNullValue) {
		threadLocal.get().setWriteMapNullValue(writeMapNullValue);
	}
	
	public static String getMsgName() {
		return threadLocal.get().getMsgName();
	}
	public static void setMsgName(String msgName) {
		threadLocal.get().setMsgName(msgName);
	}
	public static String getMsg() {
		return threadLocal.get().getMsg();
	}
	public static void setMsg(String msg) {
		threadLocal.get().setMsg(msg);
	}
	public static String getErrorsName() {
		return threadLocal.get().getErrorsName();
	}
	public static void setErrorsName(String errorsName) {
		threadLocal.get().setErrorsName(errorsName);
	}
	public static String getErrorsValue() {
		return threadLocal.get().getErrorsValue();
	}
	public static void setErrorsValue(String errorsValue) {
		threadLocal.get().setErrorsValue(errorsValue);
	}
	
	
	
	private static class ToJsonConfig {
		public transient Boolean autoWrap=true;//自动封装为某种格式
		
		public transient String filterPropertysName="filterPropertys";
		public transient String rootName="root";
		public transient String successName="success";
		public transient boolean successValue=true;
		public transient String totalName="total";
		public transient String startName="start";
		public transient String limitName="limit";
		public transient String pageNoName="page";
		public transient String msgName="msg";
		public transient String errorsName="errors";
		public transient String errorsValue="";
		
		public transient String filterPropertys=null;
		public transient Class[] filterClass=null;
		public transient Boolean enableHibernateLazyInitializerFilter=true;
		public transient String msg=null;
		
		//关闭fastjson的循环引用处理
		public transient Boolean disableCircularReferenceDetect=true;
		public transient String datePattern="yyyy-MM-dd";
		
		//添加额外的属性
		public transient Map extProperties;
		
		public transient Boolean writeMapNullValue=true;//是否输出为null的内容
		
		
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
		public Boolean getWriteMapNullValue() {
			return writeMapNullValue;
		}
		public void setWriteMapNullValue(Boolean writeMapNullValue) {
			this.writeMapNullValue = writeMapNullValue;
		}
		public String getMsgName() {
			return msgName;
		}
		public void setMsgName(String msgName) {
			this.msgName = msgName;
		}
		public void setExtProperties(Map extProperties) {
			this.extProperties = extProperties;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public boolean getSuccessValue() {
			return successValue;
		}
		public void setSuccessValue(boolean successValue) {
			this.successValue = successValue;
		}
		public String getErrorsName() {
			return errorsName;
		}
		public void setErrorsName(String errorsName) {
			this.errorsName = errorsName;
		}
		public String getErrorsValue() {
			return errorsValue;
		}
		public void setErrorsValue(String errorsValue) {
			this.errorsValue = errorsValue;
		}
		
	}
	
//	private static class Config {
//		public transient Boolean autoWrap=true;//自动封装为某种格式
//		
//		public transient String filterPropertysName="filterPropertys";
//		public transient String rootName="root";
//		public transient String successName="success";
//		public transient String totalName="total";
//		public transient String startName="start";
//		public transient String limitName="limit";
//		public transient String pageNoName="page";
//		
//		public transient String filterPropertys=null;
//		public transient Class[] filterClass=null;
//		public transient Boolean enableHibernateLazyInitializerFilter=true;
//		
//		//关闭fastjson的循环引用处理
//		public transient Boolean disableCircularReferenceDetect=true;
//		public transient String datePattern="yyyy-MM-dd";
//		
//		
//		
//		public String getFilterPropertysName() {
//			return filterPropertysName;
//		}
//		public void setFilterPropertysName(String filterPropertysName) {
//			this.filterPropertysName = filterPropertysName;
//		}
//		public String getRootName() {
//			return rootName;
//		}
//		public void setRootName(String rootName) {
//			this.rootName = rootName;
//		}
//		public String getFilterPropertys() {
//			return filterPropertys;
//		}
//		public void setFilterPropertys(String filterPropertys) {
//			this.filterPropertys = filterPropertys;
//		}
//		public void setFilterPropertys(String filterPropertys,Class... clazz) {
//			this.filterPropertys = filterPropertys;
//			this.filterClass=clazz;
//		}
//		public Boolean getEnableHibernateLazyInitializerFilter() {
//			return enableHibernateLazyInitializerFilter;
//		}
//		public void setEnableHibernateLazyInitializerFilter(
//				Boolean enableHibernateLazyInitializerFilter) {
//			this.enableHibernateLazyInitializerFilter = enableHibernateLazyInitializerFilter;
//		}
//
//		public String getSuccessName() {
//			return successName;
//		}
//		public void setSuccessName(String successName) {
//			this.successName = successName;
//		}
//		public String getTotalName() {
//			return totalName;
//		}
//		public void setTotalName(String totalName) {
//			this.totalName = totalName;
//		}
//		public String getStartName() {
//			return startName;
//		}
//		public void setStartName(String startName) {
//			this.startName = startName;
//		}
//		public String getLimitName() {
//			return limitName;
//		}
//		public void setLimitName(String limitName) {
//			this.limitName = limitName;
//		}
//		public String getPageNoName() {
//			return pageNoName;
//		}
//		public void setPageNoName(String pageNoName) {
//			this.pageNoName = pageNoName;
//		}
//		public Boolean getAutoWrap() {
//			return autoWrap;
//		}
//		public void setAutoWrap(Boolean autoWrap) {
//			this.autoWrap = autoWrap;
//		}
//		public Boolean getDisableCircularReferenceDetect() {
//			return disableCircularReferenceDetect;
//		}
//		public void setDisableCircularReferenceDetect(
//				Boolean disableCircularReferenceDetect) {
//			this.disableCircularReferenceDetect = disableCircularReferenceDetect;
//		}
//		public Class[] getFilterClass() {
//			return filterClass;
//		}
//		public void setFilterClass(Class[] filterClass) {
//			this.filterClass = filterClass;
//		}
//		public String getDatePattern() {
//			return datePattern;
//		}
//		public void setDatePattern(String datePattern) {
//			this.datePattern = datePattern;
//		}
//		
//		
//		
//	}
//	

}
