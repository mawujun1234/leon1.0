package com.mawujun.mobile.geolocation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 作业单位的定位信息，还
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_geolocation")
public class Geolocation extends UUIDEntity {
	
	@Column(length=50,nullable=false)
	private String sessionId;//回话id，可以用来区分同一天中的不同轨迹段
	@Column(length=50,nullable=false)
	private String uuid;//设备的id	
	@Column(length=50,nullable=false)
	private String loginName;//登录的用户名
	
	
	
	
	@Column(length=20)
	private String longitude;//经度
	@Column(length=20)
	private String latitude;//维度
	private Float  radius;///获取定位精度半径，单位是米
    public Float direction; // gps定位结果时，行进的方向，单位度，范围【0-360】，手机上部正朝向北的方向为0°方向
    public Float speed;// GPS速度当service的type是1，且创建该track的时候输入了这个字段才会返回。
    private Date locationDate;// gps的上传时间
	
    private Date createDate;//在服务端创建的时间
	

	
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getLocationDate() {
		return locationDate;
	}
	public void setLocationDate(Date locationDate) {
		this.locationDate = locationDate;
	}
	public Float getRadius() {
		return radius;
	}
	public void setRadius(Float radius) {
		this.radius = radius;
	}
	public Float getDirection() {
		return direction;
	}
	public void setDirection(Float direction) {
		this.direction = direction;
	}
	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	
	
}
