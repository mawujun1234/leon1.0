package com.mawujun.generator;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_PropertyConfig")
public class PropertyConfig extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectName;
	private String property;
	private String label;
	private String showModel;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getShowModel() {
		return showModel;
	}
	public void setShowModel(String showModel) {
		this.showModel = showModel;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	

}
