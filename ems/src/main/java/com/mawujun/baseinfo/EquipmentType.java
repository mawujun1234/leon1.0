package com.mawujun.baseinfo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipmenttype")
public class EquipmentType extends EquipmentTypeAbstract implements IdEntity<String>{
	@Transient
	private List<EquipmentSubtype> subtypes;

	public List<EquipmentSubtype> getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(List<EquipmentSubtype> subtypes) {
		this.subtypes = subtypes;
	}
	

}
