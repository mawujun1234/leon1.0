package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name = "ems_equipmentprod")
public class EquipmentProd  implements
		IdEntity<String> {
	
	@Id
	@Column(length=12)
	private String id;
	@Column(length=30)
	private String name;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=true;
//	
//	@Column(updatable=false)
//	private Integer levl;
	@Column(length=12)
	private String parent_id;
	@Column(length=6)
	private String subtype_id;
	@Column(length = 100)
	private String memo;//描述信息，例如 国内标配，样品等信息,先放着，现在只有 品名里面会用到

	// 单位：台，个
	@Column(length = 10)
	private String unit;
	@Column(length = 500)
	private String spec;//规格
	@Column(length = 50)
	private String style;// 型号
	@Column(length = 20)
	private String brand_id;
	
	@Enumerated(EnumType.STRING)
	@Column(length=6)
	private EquipmentProdType type=EquipmentProdType.DJ;
//	@Column(length=6)
//	private String type_parent_id;//当被拆分后的parent_id
	
	private int quality_month;//质保时间长度，以月为单位
	
	
	
	
	
	public Boolean getLeaf() {
		//如果是套件就返回false
		if(type==EquipmentProdType.TJ){
			return false;
		} else {
			return true;
		}
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
	
	public EquipmentProdType getType() {
		return type;
	}
	public void setType(EquipmentProdType type) {
		this.type = type;
	}


	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getSubtype_id() {
		return subtype_id;
	}

	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


}
