package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
@MappedSuperclass
public class EquipmentTypeAbstract {

	@Id
	@Column(length=3)
	private String id;
	@Column(length=30)
	private String name;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
	
	@Column(updatable=false)
	private Integer levl;
	@Column(length=2)
	private String parent_id;
	@Transient
	private Boolean leaf;
	
	@Transient
	private String unit; //有用，收集前端信息的时候
	@Transient
	private String spec;//有用，收集前端信息的时候
	@Transient
	private String style;// 型号
	@Transient
	private String brand_id;//有用，收集前端信息的时候
	
	
	public String getStatus_name() {
		if(this.getStatus()==true){
			return "有效";
		} else {
			return "无效";
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return this.getName()+"("+this.getId()+")";
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getLevl() {
		return levl;
	}
	public void setLevl(Integer levl) {
		this.levl = levl;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

}
