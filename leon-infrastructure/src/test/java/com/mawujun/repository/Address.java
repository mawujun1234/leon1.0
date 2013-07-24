package com.mawujun.repository;

import javax.persistence.Embeddable;


@Embeddable
public class Address {
	private String address;
	private String zip;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
