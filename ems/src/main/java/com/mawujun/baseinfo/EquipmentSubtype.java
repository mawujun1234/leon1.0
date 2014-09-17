package com.mawujun.baseinfo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipmentsubtype")
public class EquipmentSubtype  extends EquipmentTypeAbstract implements IdEntity<String>{
	
//	@Id
//	@Column(length=2)
//	private String id;
//	@Column(length=30)
//	private String text;
//	private Integer status;	
//	@Column(updatable=false)
//	private int level=2;
//	
////	@ManyToOne(fetch=FetchType.LAZY)
////	private EquipmentType parent;
////	@Transient
//	private String parent_id;
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
//	public EquipmentType getParent() {
//		return parent;
//	}
//	public void setParent(EquipmentType parent) {
//		this.parent = parent;
//	}
//	public String getParent_id() {
//		return parent_id;
//	}
//	public void setParent_id(String parent_id) {
//		this.parent_id = parent_id;
//	}

}
