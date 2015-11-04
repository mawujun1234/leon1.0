package com.mawujun.mobile.task;



public class TaskVO extends Task {
	
	//@Transient
	private String pole_code;
	private String pole_longitude;//经度
	private String pole_latitude;
	
	
	public String getPole_longitude() {
		return pole_longitude;
	}
	public void setPole_longitude(String pole_longitude) {
		this.pole_longitude = pole_longitude;
	}
	public String getPole_latitude() {
		return pole_latitude;
	}
	public void setPole_latitude(String pole_latitude) {
		this.pole_latitude = pole_latitude;
	}
	public String getPole_code() {
		return pole_code;
	}
	public void setPole_code(String pole_code) {
		this.pole_code = pole_code;
	}
	
}
