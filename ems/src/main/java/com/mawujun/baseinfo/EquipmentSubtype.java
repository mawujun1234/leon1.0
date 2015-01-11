package com.mawujun.baseinfo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipmentsubtype")
public class EquipmentSubtype  extends EquipmentTypeAbstract implements IdEntity<String>{
	@Transient
	private List<EquipmentProd> prodes;

	public List<EquipmentProd> getProdes() {
		return prodes;
	}

	public void setProdes(List<EquipmentProd> prodes) {
		this.prodes = prodes;
	}

}
