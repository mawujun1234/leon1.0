package com.mawujun.exten;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;

@MappedSuperclass
public class TreeNode  extends UUIDEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=20)
	private String icon;
	@Column(length=20)
	private String iconCls;
	@Transient
	private boolean leaf=false;
	@Transient
	private Boolean checked=null;
	@Transient
	private boolean expanded=false;
	@Transient
	private boolean edit=true;
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public Boolean isChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
