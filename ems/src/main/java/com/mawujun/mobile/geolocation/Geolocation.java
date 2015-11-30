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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
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
	private Double altitude;//获取高度信息，目前只有是GPS定位结果时才有效，单位米.默认值Double.MIN_VALUE,但是数据库存储会出问题，所以默认值就改成0
	private Float  radius;///获取定位精度半径，单位是米
    public Float direction; // gps定位结果时，行进的方向，单位度，范围【0-360】，手机上部正朝向北的方向为0°方向
    public Float speed;// GPS速度当service的type是1，且创建该track的时候输入了这个字段才会返回。单位公里/小时，默认值0.0f
    public Double distance;//在同个会话中，距离上一次地点的距离
    @Column(length=20)
    public String loc_type;//定位类型是gps定位还是网络定位
    private Date loc_time;// gps的上传时间
    private Long loc_time_interval;//两次定位的时间间隔，毫秒，主要用于过滤漂移的点位，gps_interval也可以，但是可能会存在定位数据丢失，所以自已有重新定义了一个
    								//第一个点位为0
    private Integer gps_interval;//定位时间间隔，毫秒，这是定义的时间间隔
   
	
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
	public Date getLoc_time() {
		return loc_time;
	}
	public void setLoc_time(Date loc_time) {
		this.loc_time = loc_time;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Integer getGps_interval() {
		return gps_interval;
	}
	public void setGps_interval(Integer gps_interval) {
		this.gps_interval = gps_interval;
	}
	public Long getLoc_time_interval() {
		return loc_time_interval;
	}
	public void setLoc_time_interval(Long loc_time_interval) {
		this.loc_time_interval = loc_time_interval;
	}
	public String getLoc_type() {
		return loc_type;
	}
	public void setLoc_type(String loc_type) {
		this.loc_type = loc_type;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	
}
