package com.mawujun.mobile.login;

import java.util.Date;

/**
 * gps未上传的警告
 * @author mawujun 16064988@qq.com  
 *
 */
public class WaringGps {
	
	private String loginName;
	private Date loginTime;
	
	private Boolean isUploadGps=false;
	private Date lastedUploadTime;//最近一次上传时间
	private String lasted_longitude;
	private String lasted_latitude;
	
	
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
	

}
