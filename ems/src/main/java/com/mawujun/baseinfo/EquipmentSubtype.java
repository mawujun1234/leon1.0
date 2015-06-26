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
	private List<EquipmentProdVO> prodes;

	public List<EquipmentProdVO> getProdes() {
		return prodes;
	}

	public void setProdes(List<EquipmentProdVO> prodes) {
		this.prodes = prodes;
	}

}
