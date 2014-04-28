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
//@Entity
//@Table(name="leon_role_role")
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
	@Column(length=10,insertable = false,updatable = false)
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
	/**
	 * 等同于getCurrent
	 * @author mawujun 16064988@qq.com  
	 * @return
	 */
	public Role getParent() {
		return getCurrent();
	}

	public void setCurrent(Role current) {
		this.current = current;
		this.id.currentId = current.getId();
		//current.getCurrents().add(this);
	}

	public Role getOther() {
		return other;
	}
	/**
	 * 等同于getOther
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public Role getChild() {
		return getOther();
	}

	public void setOther(Role other) {
		this.other = other;
		this.id.otherId = other.getId();
		//other.getOthers().add(this);
	}

	public RoleRoleEnum getRoleRoleEnum() {
		return roleRoleEnum;
	}

	public void setRoleRoleEnum(RoleRoleEnum roleRoleEnum) {
		this.roleRoleEnum = roleRoleEnum;
		this.id.roleRoleEnum =roleRoleEnum.toString();
	}
	public void setRoleRoleEnum(String roleRoleEnum) {
		this.roleRoleEnum = RoleRoleEnum.valueOf(roleRoleEnum);
		this.id.roleRoleEnum =roleRoleEnum;
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
		@Column(name = "roleRoleEnum")
		private String roleRoleEnum;

		public Id() {
		}

		public Id(String currentId, String otherId,String roleRoleEnum) {
			this.currentId = currentId;
			this.otherId = otherId;
			this.roleRoleEnum=roleRoleEnum;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				return this.currentId.equals(that.currentId)
						&& this.otherId.equals(that.otherId)&&this.roleRoleEnum.equals(that.roleRoleEnum);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return currentId.hashCode() + otherId.hashCode()+roleRoleEnum.hashCode();
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

		public String getRoleRoleEnum() {
			return roleRoleEnum;
		}

		public void setRoleRoleEnum(String roleRoleEnum) {
			this.roleRoleEnum = roleRoleEnum;
		}

	}


}
