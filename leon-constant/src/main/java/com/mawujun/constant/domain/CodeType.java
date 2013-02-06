package com.mawujun.constant.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 常数类，下面有CodeItem
 * @author mawujun
 *
 */
@Entity
@Table(name="t_sys_CodeType")
public class CodeType implements IdEntity<String> {
	
	@Id
	@Column(length=25)
	private String code;
	@Column(length=25)
	private String name;
	@Column(length=200)
	private String remark;
	
	@ManyToOne
	@JoinColumn(name="codeCategory_id")
	private CodeCategory codeCategory;
	
	@OneToMany(mappedBy="codeType")
	@OrderBy("ordering")
	private Set<CodeItem> codeItemes;

	public String getId() {
		// TODO Auto-generated method stub
		return this.code;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		this.code=id;
	}

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

	public CodeCategory getCodeCategory() {
		return codeCategory;
	}

	public void setCodeCategory(CodeCategory codeCategory) {
		this.codeCategory = codeCategory;
	}

	public Set<CodeItem> getCodeItemes() {
		return codeItemes;
	}

	public void setCodeItemes(Set<CodeItem> codeItemes) {
		this.codeItemes = codeItemes;
	}




}
