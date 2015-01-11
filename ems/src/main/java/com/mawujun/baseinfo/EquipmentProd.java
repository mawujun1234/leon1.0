package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name = "ems_equipmentprod")
public class EquipmentProd extends EquipmentTypeAbstract implements
		IdEntity<String> {

	// 单位：台，个
	@Column(length = 10)
	private String unit;
	@Column(length = 500)
	private String spec;
	@Column(length = 50)
	private String style;// 型号
	@Column(length = 20)
	private String brand_id;
	
	@Transient
	private String brand_name;
	@Transient
	private String subtype_name;
	@Transient
	private String ecode_num;//品名下的ecode的数量，或者是
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getEcode_num() {
		return ecode_num;
	}
	public void setEcode_num(String ecode_num) {
		this.ecode_num = ecode_num;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}

}
