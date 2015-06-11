package com.mawujun.baseinfo;

import java.io.Serializable;

public class EquipmentPolePK implements Serializable{
	private static final long serialVersionUID = 1L;
	//@Column(length=36)
	private String ecode;//条码ecode或品名id
	//@Column(length=36)
	private String pole_id;
	
	public EquipmentPolePK() {
		super();
	}
	public EquipmentPolePK(String ecode, String pole_id) {
		super();
		this.ecode = ecode;
		this.pole_id = pole_id;
	}
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getPole_id() {
		return pole_id;
	}
	public void setPole_id(String pole_id) {
		this.pole_id = pole_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ecode == null) ? 0 : ecode.hashCode());
		result = prime * result + ((pole_id == null) ? 0 : pole_id.hashCode());
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
		EquipmentPolePK other = (EquipmentPolePK) obj;
		if (ecode == null) {
			if (other.ecode != null)
				return false;
		} else if (!ecode.equals(other.ecode))
			return false;
		if (pole_id == null) {
			if (other.pole_id != null)
				return false;
		} else if (!pole_id.equals(other.pole_id))
			return false;
		return true;
	}
}
