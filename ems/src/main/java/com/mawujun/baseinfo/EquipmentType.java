package com.mawujun.baseinfo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipmenttype")
public class EquipmentType extends EquipmentTypeAbstract implements IdEntity<String>{
//	
//	@Id
//	@Column(length=2)
//	private String id;
//	@Column(length=30)
//	private String text;
//	private Integer status;
//	
//	@Column(updatable=false)
//	private int level=1;
//	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getText() {
//		return text;
//	}
//	public void setText(String text) {
//		this.text = text;
//	}
//	public Integer getStatus() {
//		return status;
//	}
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//	public int getLevel() {
//		return level;
//	}
//	public void setLevel(int level) {
//		this.level = level;
//	}

}
