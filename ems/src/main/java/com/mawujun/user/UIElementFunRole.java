package com.mawujun.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 角色和界面元素的关联
 * @author mawujun
 *
 */
@Entity(name="sys_uielement_funrole")
public class UIElementFunRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @ManyToOne
	private UIElement uIElement;
	@Id
    @ManyToOne
	private FunRole funRole;
	public UIElementFunRole() {}

	public UIElementFunRole(UIElement uIElement, FunRole funRole) {
		super();
		this.uIElement = uIElement;
		this.funRole = funRole;
	}
	
	public UIElement getuIElement() {
		return uIElement;
	}
	public void setuIElement(UIElement uIElement) {
		this.uIElement = uIElement;
	}
	public FunRole getFunRole() {
		return funRole;
	}
	public void setFunRole(FunRole funRole) {
		this.funRole = funRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funRole == null) ? 0 : funRole.hashCode());
		result = prime * result
				+ ((uIElement == null) ? 0 : uIElement.hashCode());
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
		UIElementFunRole other = (UIElementFunRole) obj;
		if (funRole == null) {
			if (other.funRole != null)
				return false;
		} else if (!funRole.equals(other.funRole))
			return false;
		if (uIElement == null) {
			if (other.uIElement != null)
				return false;
		} else if (!uIElement.equals(other.uIElement))
			return false;
		return true;
	}
	
}
