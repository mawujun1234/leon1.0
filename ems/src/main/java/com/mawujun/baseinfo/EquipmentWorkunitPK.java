package com.mawujun.baseinfo;

import java.io.Serializable;

public class EquipmentWorkunitPK implements Serializable{
	private static final long serialVersionUID = 1L;
	//@Column(length=36)
	private String ecode;//条码ecode或品名id
	//@Column(length=36)
	private String workunit_id;
	
	public EquipmentWorkunitPK() {
		super();
	}
	public EquipmentWorkunitPK(String ecode, String workunit_id) {
		super();
		this.ecode = ecode;
		this.workunit_id = workunit_id;
	}
	
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public String getWorkunit_id() {
		return workunit_id;
	}
	public void setWorkunit_id(String workunit_id) {
		this.workunit_id = workunit_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ecode == null) ? 0 : ecode.hashCode());
		result = prime * result + ((workunit_id == null) ? 0 : workunit_id.hashCode());
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
		EquipmentWorkunitPK other = (EquipmentWorkunitPK) obj;
		if (ecode == null) {
			if (other.ecode != null)
				return false;
		} else if (!ecode.equals(other.ecode))
			return false;
		if (workunit_id == null) {
			if (other.workunit_id != null)
				return false;
		} else if (!workunit_id.equals(other.workunit_id))
			return false;
		return true;
	}
	
}
