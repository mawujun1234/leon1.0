package com.mawujun.desktop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name = "leon_QuickStart")
public class QuickStart implements IdEntity<QuickStart.Id>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Embeddable
	public static class Id implements Serializable {
		@Column(name = "user_id")
		private String userId;
		@Column(name = "menuItem_id")
		private String menuItemId;

		public Id() {
		}
		
		public static Id getInstance(String user_id, String menuItem_id){
			return new Id(user_id,menuItem_id);
		}

		public Id(String user_id, String menuItem_id) {
			this.userId = user_id;
			this.menuItemId = menuItem_id;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id) o;
				return this.userId.equals(that.userId)
						&& this.menuItemId.equals(that.menuItemId);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return userId.hashCode() + menuItemId.hashCode();
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getMenuItemId() {
			return menuItemId;
		}

		public void setMenuItemId(String menuItemId) {
			this.menuItemId = menuItemId;
		}
	}

	@EmbeddedId
	private Id id = new Id();

	@Override
	public void setId(QuickStart.Id id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public QuickStart.Id getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
