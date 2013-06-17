package com.mawujun.repository.idEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.mawujun.annotation.Label;
import com.mawujun.repository.hibernate.validate.ValidateEntity;

/**
 * 统一定义id的entity基类.是使用自动生成主键作为生成策略
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author mawujun
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class AutoIdEntity<ID> extends ValidateEntity implements IdEntity<ID> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@org.hibernate.annotations.AccessType("property")
	//@Access(AccessType.PROPERTY)
	@Label(name="id")
	protected ID id;

	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutoIdEntity other = (AutoIdEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}