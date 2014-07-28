package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_pole")
public class Pole extends UUIDEntity {
	@Column(length=60)
	private String name;
	@Column(length=100)
	private String address;
	@Column(length=15)
	private String longitude;//经度
	@Column(length=15)
	private String latitude;//纬度
	
	private String customer_id;

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

}
