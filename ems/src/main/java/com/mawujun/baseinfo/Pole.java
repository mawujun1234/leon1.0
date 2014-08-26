package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_pole")
public class Pole extends UUIDEntity {
	@Column(length=60)
	private String name;
	@Column(length=20)
	private String province;
	@Column(length=20)
	private String city;
	@Column(length=20)
	private String area;//区/县
	@Column(length=50)
	private String address;//详细地址
	
	
	@Column(length=15)
	private String longitude;//经度
	@Column(length=15)
	private String latitude;//纬度
	
	@Column(length=36)
	private String customer_id;
	
	@Column(length=36)
	private String area_id;
	
//	@org.hibernate.annotations.Type(type="yes_no")
//	private Boolean status=true;
	
	@Column(length=15)
	@Enumerated(EnumType.STRING)
	private PoleStatus status;

	public String getStatus_name() {
		if(status==null){
			return null;
		}
		return status.getName();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public PoleStatus getStatus() {
		return status;
	}

	public void setStatus(PoleStatus status) {
		this.status = status;
	}

}
