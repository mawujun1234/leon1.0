package com.mawujun.repository.idEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.repository.hibernate.validate.ValidateEntity;

/**
 * 统一定义id的entity基类.是使用UUID作为生成策略
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * @author mawujun
 *
 */
@MappedSuperclass
public abstract class UUIDEntityValidate  extends ValidateEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	//@GenericGenerator(name = "idGenerator", strategy = "com.mawujun.model.UUIDGenerator")
	//@GeneratedValue(generator="idGenerator")
	@Column(length=36,updatable=false,unique=true)
	//@Access(AccessType.PROPERTY)
	@org.hibernate.annotations.AccessType("property")
	protected String id;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
