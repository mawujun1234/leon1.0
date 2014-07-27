package com.mawujun.baseinfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
@MappedSuperclass
public class EquipmentTypeVO {

	@Id
	@Column(length=2)
	private String id;
	@Column(length=30)
	private String text;
	private Integer status;
	
	@Column(updatable=false)
	private Integer levl;
	@Column(length=2)
	private String parent_id;
	@Transient
	private Boolean leaf;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getLevl() {
		return levl;
	}
	public void setLevl(Integer levl) {
		this.levl = levl;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

}
