package com.mawujun.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mawujun.annotation.AttrName;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_menu")
public class Menu  extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AttrName(name="名称")
	@Column(length=20)
	private String text;
	public Menu(){
		super();
	}
	public Menu(String id){
		super();
		this.id=id;
		
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
