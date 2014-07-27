package com.mawujun.generator;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 用来配置某个领域模型的某个属性，在界面上的展现形式的
 * 是用来生成界面组件的时候用的
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="leon_propertyconfig")
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
