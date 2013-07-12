package com.mawujun.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.mawujun.annotation.Label;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class Menu  extends UUIDEntity{
	public final static String default_id="default";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Label(name="名称")
	@Column(length=20)
	private String text;
//	@Column(length=36)
//	private String rootId;//菜单的根菜单项的id
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
//	public String getRootId() {
//		return rootId;
//	}
//	public void setRootId(String rootId) {
//		this.rootId = rootId;
//	}
}
