package com.mawujun.role;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 */
@Entity
@Table(name="leon_role_role")
public class RoleRole extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前的角色，如果属于继承关系，那current就是子角色
	 * 如果属于互斥关系，那current就是当前关系。
	 */
	@ManyToOne
	@JoinColumn(name="current_id",insertable = false,updatable = false)
	private Role current;
	//父角色或者是互斥的角色
	@ManyToOne
	@JoinColumn(name="other_id",insertable = false,updatable = false)
	private Role other;
	@Enumerated(EnumType.STRING)
	private RoleRoleEnum roleRoleEnum;
	
	@Column(updatable=false)
	private Date createDate;

	public Role getCurrent() {
		return current;
	}

	public void setCurrent(Role current) {
		this.current = current;
	}

	public Role getOther() {
		return other;
	}

	public void setOther(Role other) {
		this.other = other;
	}

	public RoleRoleEnum getRoleRoleEnum() {
		return roleRoleEnum;
	}

	public void setRoleRoleEnum(RoleRoleEnum roleRoleEnum) {
		this.roleRoleEnum = roleRoleEnum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
