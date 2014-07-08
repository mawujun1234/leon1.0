package com.mawujun.role;


import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.mawujun.fun.Fun;
import com.mawujun.repository.idEntity.UUIDEntity;



@Entity
@Table(name = "leon_role_fun")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class RoleFun extends UUIDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	private Role role;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	private Fun fun;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private PermissionEnum permissionEnum;
	
	@Column(updatable=false)
	@NotNull
	private Date createDate;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Fun getFun() {
		return fun;
	}
	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}
	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}
	public void setPermissionEnum(String permissionEnum) {
		this.permissionEnum = PermissionEnum.valueOf(permissionEnum);
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	


}
