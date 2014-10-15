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
	private String uuid;
	@Column(length=50,nullable=false)
	private String loginName;
	private Date createDate;//上传时间
	
	@Column(length=20)
	private String longitude;//经度
	@Column(length=20)
	private String latitude;//维度
	@Column(length=20)
	private String radius;///获取定位精度半径，单位是米
//	@Column(length=10)
//	private String direction;////获得手机方向，范围【0-360】，手机上部正朝向北的方向为0°方向
	
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
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	
}
