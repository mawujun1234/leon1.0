package com.mawujun.baseinfo;

import java.io.Serializable;

import javax.persistence.Embeddable;

//@Embeddable
public class EquipmentStorePK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String encode;
	private String store_id;
	
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
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
		result = prime * result + ((encode == null) ? 0 : encode.hashCode());
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
		EquipmentStorePK other = (EquipmentStorePK) obj;
		if (encode == null) {
			if (other.encode != null)
				return false;
		} else if (!encode.equals(other.encode))
			return false;
		if (store_id == null) {
			if (other.store_id != null)
				return false;
		} else if (!store_id.equals(other.store_id))
			return false;
		return true;
	}

}
