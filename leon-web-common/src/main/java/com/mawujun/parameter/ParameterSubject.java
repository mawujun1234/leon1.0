package com.mawujun.parameter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="leon_parameter_subject")
public class ParameterSubject implements IdEntity<ParameterSubject.Id>,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	

	private String parameterValue;//参数值
	
	@EmbeddedId
	private Id id = new Id();

	@Override
	public void setId(ParameterSubject.Id id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public ParameterSubject.Id getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	
	@Embeddable
	public static class Id implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(length=36,name = "subject_id")
		private String subjectId;
		@Column(length=36,name = "parameter_id")
		private String parameterId;
		@Enumerated(EnumType.STRING)
		private SubjectEnum subjectType;//

		public Id() {
		}
		
		public static Id getInstance(String subject_id, String parameter_id,SubjectEnum subjectType){
			return new Id(subject_id,parameter_id,subjectType);
		}

		public Id(String subject_id, String parameter_id,SubjectEnum subjectType) {
			this.subjectId = subject_id;
			this.parameterId = parameter_id;
			this.subjectType = subjectType;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				return this.subjectId.equals(that.subjectId)
						&& this.parameterId.equals(that.parameterId)&& this.subjectType.equals(that.subjectType);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return subjectId.hashCode() + parameterId.hashCode()+ subjectType.hashCode();
		}

		public String getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(String subjectId) {
			this.subjectId = subjectId;
		}

		public String getParameterId() {
			return parameterId;
		}

		public void setParameterId(String parameterId) {
			this.parameterId = parameterId;
		}

		public SubjectEnum getSubjectType() {
			return subjectType;
		}

		public void setSubjectType(SubjectEnum subjectType) {
			this.subjectType = subjectType;
		}

	}

}
