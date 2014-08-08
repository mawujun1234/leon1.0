package com.mawujun.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="sys_funrole")
public class FunRole  extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=30)
	private String text;
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean leaf; // 是否为叶子结点
	@Column(length=100)
	private String memo;
	@Column(length=36)
	private String parentId;
	
	@Transient
	private String cls; // 显示的样式，file、folder
	@Transient
	private String iconCls; // 结点图标样式
	
	@Transient
	private Boolean checked;//用于界面上显示的
	

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	
}
