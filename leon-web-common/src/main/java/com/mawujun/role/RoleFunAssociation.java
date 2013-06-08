package com.mawujun.role;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;



@Entity
@Table(name = "leon_Role_Fun")
public class RoleFunAssociation extends UUIDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "role_id")
	protected String roleId;
	@Column(name = "fun_id")
	protected String funId;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private PermissionTypeEnum permissionType;
	//@Column()
	private Date createDate;
	



	public PermissionTypeEnum getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionTypeEnum permissionType) {
		this.permissionType = permissionType;
	}
	
	public void setPermissionType(String permissionType) {
		this.permissionType = PermissionTypeEnum.valueOf(permissionType);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
