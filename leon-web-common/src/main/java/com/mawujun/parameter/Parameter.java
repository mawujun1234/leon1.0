package com.mawujun.parameter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="leon_Parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
public class Parameter  implements IdEntity<String>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable=false)
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
	private ParameterValuesSourceEnum valueEnum;
//	@Column(length=50)
//	private String defaultValue;
	
	@Column(length=200)
	private String content;
	@Column(length=50)
	private String subjects;//应用的主体
	
	private Integer sort=0;
	@Column(length=150)
	private String validation;
	

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



	public ParameterValuesSourceEnum getValueEnum() {
		return valueEnum;
	}
	public String getValueEnumName() {
		return valueEnum==null?null:valueEnum.getName();
	}

	public void setValueEnum(ParameterValuesSourceEnum valueEnum) {
		this.valueEnum = valueEnum;
	}

	public String getContent() {
		
		return content;
	}
	
	

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubjects() {
		return subjects;
	}
	
	public String getSubjectNames() {
		if(!StringUtils.hasText(this.subjects)){
			return null;
		}
		String temp=this.subjects.substring(1, this.subjects.length()-1);
		String tempArray[]=temp.split(",");
		StringBuilder builder=new StringBuilder();
		for(String str:tempArray){
			builder.append(",");
			builder.append(SubjectEnum.valueOf(str.replace('"', ' ').trim()).getName());
		}
		return builder.toString().substring(1);
	}

	public void setSubjects(String targets) {
		this.subjects = targets;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}


}
