package com.mawujun.role;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 */
@Entity
@Table(name="leon_role_role")
public class RoleRole implements IdEntity<RoleRole.Id> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private Id id = new Id();

	
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
	@Column(length=10,nullable=false)
	private RoleRoleEnum roleRoleEnum;
	
	@Column(updatable=false)
	private Date createDate;
	
//	public RoleRole(RoleRoleEnum roleRoleEnum, Role current, Role other) {
//		// Set fields
//		this.roleRoleEnum = roleRoleEnum;
//		this.current = current;
//		this.other = other;
//		// Set identifier values
//		this.id.currentId = current.getId();
//		this.id.otherId = other.getId();
////		// 下面注释的这两句很重要
////		current.getCategorizedItems().add(this);
////		other.getCategorizedItems().add(this);
//	}


	public Role getCurrent() {
		return current;
	}

	public void setCurrent(Role current) {
		this.current = current;
		this.id.currentId = current.getId();
	}

	public Role getOther() {
		return other;
	}

	public void setOther(Role other) {
		this.other = other;
		this.id.otherId = other.getId();
	}

	public RoleRoleEnum getRoleRoleEnum() {
		return roleRoleEnum;
	}

	public void setRoleRoleEnum(RoleRoleEnum roleRoleEnum) {
		this.roleRoleEnum = roleRoleEnum;
	}
	public void setRoleRoleEnum(String roleRoleEnum) {
		this.roleRoleEnum = RoleRoleEnum.valueOf(roleRoleEnum);
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setId(Id id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	//@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		//this.id=id;
	}

	//@Override
	public Id getId() {
		// TODO Auto-generated method stub
		return this.id;
	}


	@Embeddable
	public static class Id implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "current_id")
		private String currentId;
		@Column(name = "other_id")
		private String otherId;

		public Id() {
		}

		public Id(String currentId, String otherId) {
			this.currentId = currentId;
			this.otherId = otherId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				return this.currentId.equals(that.currentId)
						&& this.otherId.equals(that.otherId);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return currentId.hashCode() + otherId.hashCode();
		}

		public String getCurrentId() {
			return currentId;
		}

		public void setCurrentId(String currentId) {
			this.currentId = currentId;
		}

		public String getOtherId() {
			return otherId;
		}

		public void setOtherId(String otherId) {
			this.otherId = otherId;
		}
		
//		public String toString(){
//			return this.currentId+"=="+this.otherId;
//		}
	}


}
