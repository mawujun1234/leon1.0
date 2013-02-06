package com.mawujun.repository.idEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.mawujun.repository.hibernate.validate.ValidateEntity;

/**
 * guid 采用数据库底层的guid算法机制，对应MySQL的uuid()函数，SQL Server的newid()函数，ORCALE的rawtohex(sys_guid())函数等
 * @author mawujun
 *
 */
@MappedSuperclass
public abstract class GUIDValidateEntity extends ValidateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "guid")
	@Column(length=36,updatable=false,unique=true)
	@org.hibernate.annotations.AccessType("property")
	protected String id;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
