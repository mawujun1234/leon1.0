package com.mawujun.mobile.geolocation;

import java.util.Date;

/**
 * 轨迹列表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class Trace {
	
	private String sessionId;
	private String uuid;//设备的id	
	private String loginName;//登录的用户名
	
	private Date startDate;
	private Date endDate;
	private String duration;//时长
	private Double distance;//距离
	
	
	public String getDuration() {
		
		if(duration!=null && !"".equals(duration)){
			return duration;
		}
		long l=endDate.getTime()-startDate.getTime();   
		long day=l/(24*60*60*1000);   
		long hour=(l/(60*60*1000)-day*24);   
		long min=((l/(60*1000))-day*24*60-hour*60);   
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);   
		//   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");  
		 setDuration(hour+"小时"+min+"分"+s+"秒");
		return duration;
	}
	
	public Double getDistance_km() {
		if(distance!=null){
			return Math.round(distance/1000*100)/100.0;
		} else {
			return 0.0;
		}
	}


	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(String Double) {
		this.distance = distance;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
