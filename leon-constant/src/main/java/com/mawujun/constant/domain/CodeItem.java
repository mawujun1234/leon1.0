package com.mawujun.constant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.AutoIdEntity;

@Entity
@Table(name="t_sys_CodeItem",uniqueConstraints={
		@UniqueConstraint(columnNames = {"code","codeType_code"})
})
public class CodeItem extends AutoIdEntity<Long> {
	@Column(length=25)
	private String code;//具体的值，例如0,1,2等这些代码
	@Column(length=25)
	private String name;//0,1等的名词，如有效，无效等
	@Column(length=200)
	private String remark;
	private int ordering;//排序
	@ManyToOne
	private CodeType codeType;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public CodeType getCodeType() {
		return codeType;
	}
	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}
	
	
	
}
