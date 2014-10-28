package com.mawujun.report;

import java.io.Serializable;

public class BuildDayReport_PK  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123L;

	private Integer day;//20140101这种形式

	private String subtype_id;

	private String prod_id;//品名id

	private String brand_id;//品牌id

	private String style;

	private String store_id;//仓库id，所属仓库

	
	public BuildDayReport_PK() {  
        super();
    } 
	public BuildDayReport_PK(Integer day, String subtype_id, String prod_id,
			String brand_id, String style, String store_id) {
		super();
		this.day = day;
		this.subtype_id = subtype_id;
		this.prod_id = prod_id;
		this.brand_id = brand_id;
		this.style = style;
		this.store_id = store_id;
	}

	public String getSubtype_id() {
		return subtype_id;
	}

	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brand_id == null) ? 0 : brand_id.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((prod_id == null) ? 0 : prod_id.hashCode());
		result = prime * result
				+ ((store_id == null) ? 0 : store_id.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		result = prime * result
				+ ((subtype_id == null) ? 0 : subtype_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildDayReport_PK other = (BuildDayReport_PK) obj;
		if (brand_id == null) {
			if (other.brand_id != null)
				return false;
		} else if (!brand_id.equals(other.brand_id))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (prod_id == null) {
			if (other.prod_id != null)
				return false;
		} else if (!prod_id.equals(other.prod_id))
			return false;
		if (store_id == null) {
			if (other.store_id != null)
				return false;
		} else if (!store_id.equals(other.store_id))
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		if (subtype_id == null) {
			if (other.subtype_id != null)
				return false;
		} else if (!subtype_id.equals(other.subtype_id))
			return false;
		return true;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}

}
