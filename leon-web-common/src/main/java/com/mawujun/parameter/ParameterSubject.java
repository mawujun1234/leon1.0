package com.mawujun.parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_parameter_subject")
public class ParameterSubject extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=36)
	private String subjectId;
	@Enumerated(EnumType.STRING)
	private SubjectType subjectType;//
	
//	@ManyToOne(fetch=)
//	private Parameter parameter;//参数
	private String parameterId;
	private String parameterValue;//参数值
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public SubjectType getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}
	public String getParameterId() {
		return parameterId;
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	

}
