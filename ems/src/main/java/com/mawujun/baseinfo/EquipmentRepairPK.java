package com.mawujun.baseinfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//@Embeddable
public class EquipmentRepairPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Column(length=36)
	private String ecode;//条码ecode或品名id
	//@Column(length=36)
	private String repair_id;
	
	public EquipmentRepairPK() {
		super();
	}
	public EquipmentRepairPK(String equipment_id, String store_id) {
		super();
		this.ecode = equipment_id;
		this.repair_id = store_id;
	}
	/**
	 * 条码ecode或品名id
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public String getEcode() {
		return ecode;
	}
	/**
	 * 条码ecode或品名id
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipment_id
	 */
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getRepair_id() {
		return repair_id;
	}
	public void setRepair_id(String repair_id) {
		this.repair_id = repair_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ecode == null) ? 0 : ecode.hashCode());
		result = prime * result + ((repair_id == null) ? 0 : repair_id.hashCode());
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
		EquipmentRepairPK other = (EquipmentRepairPK) obj;
		if (ecode == null) {
			if (other.ecode != null)
				return false;
		} else if (!ecode.equals(other.ecode))
			return false;
		if (repair_id == null) {
			if (other.repair_id != null)
				return false;
		} else if (!repair_id.equals(other.repair_id))
			return false;
		return true;
	}
	

}
