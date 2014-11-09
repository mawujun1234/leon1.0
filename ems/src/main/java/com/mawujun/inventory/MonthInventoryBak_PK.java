package com.mawujun.inventory;

public class MonthInventoryBak_PK extends MonthInventory_PK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer monthkey;//201401这种形式

	private String subtype_id;

	private String prod_id;//品名id

	private String brand_id;//品牌id

	private String style;

	private String store_id;//仓库id，所属仓库
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getMonthkey() {
		return monthkey;
	}

	public void setMonthkey(Integer monthkey) {
		this.monthkey = monthkey;
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
		int result = super.hashCode();
		result = prime * result
				+ ((brand_id == null) ? 0 : brand_id.hashCode());
		result = prime * result
				+ ((monthkey == null) ? 0 : monthkey.hashCode());
		result = prime * result + ((prod_id == null) ? 0 : prod_id.hashCode());
		result = prime * result
				+ ((store_id == null) ? 0 : store_id.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		result = prime * result
				+ ((subtype_id == null) ? 0 : subtype_id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthInventoryBak_PK other = (MonthInventoryBak_PK) obj;
		if (brand_id == null) {
			if (other.brand_id != null)
				return false;
		} else if (!brand_id.equals(other.brand_id))
			return false;
		if (monthkey == null) {
			if (other.monthkey != null)
				return false;
		} else if (!monthkey.equals(other.monthkey))
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
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
