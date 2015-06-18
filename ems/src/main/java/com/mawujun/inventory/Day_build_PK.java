package com.mawujun.inventory;

import java.io.Serializable;

public class Day_build_PK  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 123L;

	private Integer daykey;//20140101这种形式

	private String prod_id;//品名id

	private String store_id;//仓库id，所属仓库

	
	public Day_build_PK() {  
        super();
    } 
	public Day_build_PK(Integer day, String prod_id, String store_id) {
		super();
		this.daykey = day;
		this.prod_id = prod_id;
		this.store_id = store_id;
	}
	public Integer getDaykey() {
		return daykey;
	}
	public void setDaykey(Integer daykey) {
		this.daykey = daykey;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daykey == null) ? 0 : daykey.hashCode());
		result = prime * result + ((prod_id == null) ? 0 : prod_id.hashCode());
		result = prime * result + ((store_id == null) ? 0 : store_id.hashCode());
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
		Day_build_PK other = (Day_build_PK) obj;
		if (daykey == null) {
			if (other.daykey != null)
				return false;
		} else if (!daykey.equals(other.daykey))
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
		return true;
	}

	
}
