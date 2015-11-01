package com.mawujun.mobile.login;

import java.util.Date;

/**
 * gps未上传的警告
 * @author mawujun 16064988@qq.com  
 *
 */
public class WaringGps {
	
	private String loginName;
	private Boolean isUploadGps=false;
	private Date lastedUploadTime;//最近一次上传时间
	private Date loginTime;
	
	
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
	

}
