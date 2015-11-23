package com.mawujun.mobile.login;

import java.util.Date;
import java.util.List;

import com.mawujun.mobile.geolocation.TraceList;

/**
 * gps未上传的警告
 * @author mawujun 16064988@qq.com  
 *
 */
public class WaringGps {
	private String sessionId;
	private String loginName;
	private String name;//作业单位名称
	private String phone;
	private Date loginTime;
	
	private Boolean isUploadGps=false;
	private Date lastedUploadTime;//最近一次上传时间
	private String lasted_longitude;
	private String lasted_latitude;
	
	//private List<TraceList> traceListes;
	
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Boolean getIsUploadGps() {
		return isUploadGps;
	}
	public void setIsUploadGps(Boolean isUploadGps) {
		this.isUploadGps = isUploadGps;
	}
	public Date getLastedUploadTime() {
		return lastedUploadTime;
	}
	public void setLastedUploadTime(Date lastedUploadTime) {
		this.lastedUploadTime = lastedUploadTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLasted_longitude() {
		return lasted_longitude;
	}
	public void setLasted_longitude(String lasted_longitude) {
		this.lasted_longitude = lasted_longitude;
	}
	public String getLasted_latitude() {
		return lasted_latitude;
	}
	public void setLasted_latitude(String lasted_latitude) {
		this.lasted_latitude = lasted_latitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
//	public List<TraceList> getTraceListes() {
//		return traceListes;
//	}
//	public void setTraceListes(List<TraceList> traceListes) {
//		this.traceListes = traceListes;
//	}
	

}
