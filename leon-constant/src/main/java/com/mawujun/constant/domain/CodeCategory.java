package com.mawujun.constant.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.AutoIdEntity;

/**
 * 常数代码的类型 分类
 * @author mawujun
 *
 */
@Entity
@Table(name="t_sys_CodeCategory")
public class CodeCategory extends AutoIdEntity<Long> {
	@Column(length=25)
	private String name;
	@Column(length=200)
	private String remark;
	
	@OneToMany(mappedBy="codeCategory")
	private Set<CodeType> codeTypes;

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

	public Set<CodeType> getCodeTypes() {
		return codeTypes;
	}

	public void setCodeTypes(Set<CodeType> codeTypes) {
		this.codeTypes = codeTypes;
	}
	

}
