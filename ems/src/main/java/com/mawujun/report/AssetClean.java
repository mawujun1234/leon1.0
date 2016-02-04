package com.mawujun.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_assetclean")
public class AssetClean extends UUIDEntity{
	
	@Column(length=25)
	private String ecode;
	
	private Integer hava_day;//可使用天数
	private Integer used_day;//已经使用天数
	private Double original_value;//原值
	private Double depreciation;//折旧
	private Double net_value;//净值
	
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Integer getHava_day() {
		return hava_day;
	}
	public void setHava_day(Integer hava_day) {
		this.hava_day = hava_day;
	}
	public Integer getUsed_day() {
		return used_day;
	}
	public void setUsed_day(Integer used_day) {
		this.used_day = used_day;
	}
	public Double getOriginal_value() {
		return original_value;
	}
	public void setOriginal_value(Double original_value) {
		this.original_value = original_value;
	}
	public Double getNet_value() {
		return net_value;
	}
	public void setNet_value(Double net_value) {
		this.net_value = net_value;
	}
	public Double getDepreciation() {
		return depreciation;
	}
	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

}
