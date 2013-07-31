package com.mawujun.parameter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="leon_Parameter")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(
//    name="parameterType",
//    discriminatorType=DiscriminatorType.STRING
//)
public class Parameter  implements IdEntity<String>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;//自己设置的

	@Column(length=50)
	private String name;
	@Column(length=200)
	private String desc;
	
	
	
	@Column(length=50)
	@Enumerated(EnumType.STRING)
	private ShowModelEnum showModel;//展现方式
	
	
	@Column(length=200)
	@Enumerated(EnumType.STRING)
	private ParameterValueEnum valueEnum;
	@Column(length=50)
	private String defaultValue;
	
	@Column(length=200)
	private String content;
	

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ShowModelEnum getShowModel() {
		return showModel;
	}

	public String getShowModelName() {
		return showModel==null?null:showModel.getName();
	}
	public void setShowModel(ShowModelEnum showModel) {
		this.showModel = showModel;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ParameterValueEnum getValueEnum() {
		return valueEnum;
	}
	public String getValueEnumName() {
		return valueEnum==null?null:valueEnum.getName();
	}

	public void setValueEnum(ParameterValueEnum valueEnum) {
		this.valueEnum = valueEnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
