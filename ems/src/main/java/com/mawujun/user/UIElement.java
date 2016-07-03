package com.mawujun.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * j界面元素
 * @author mawujun
 *
 */
@Entity
@Table(name="sys_uielement")
public class UIElement  extends UUIDEntity {
	@Column(length=30)
	private String text;
	@Column(length=100,unique=true)
	private String code;
	@Column(length=36)
	private String navigation_id;
	@Column(length=200)
	private String memo;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNavigation_id() {
		return navigation_id;
	}
	public void setNavigation_id(String navigation_id) {
		this.navigation_id = navigation_id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
