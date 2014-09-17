package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="ems_equipmentprod")
public class EquipmentProd  extends EquipmentTypeAbstract implements IdEntity<String>{
	
	
//	@Id
//	@Column(length=2)
//	private String id;
//	@Column(length=30)
//	private String text;
//	private Integer status;
//	
//	//@Transient
//	@Column(updatable=false)
//	private int level=3;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	private EquipmentSubtype parent;
//	@Transient
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

}
