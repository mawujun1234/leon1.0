package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.annotation.AttrName;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_Fun")
public class Fun extends UUIDEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@AttrName(name="助记码")
	@Column(length=20)
	private String code;//助记码
	@Column(length=20)
	@AttrName(name="名称")
	private String text;
	@Column(length=80)
	@AttrName(name="地址")
	private String url;
	//private String iconCls;
	@Column(length=40)
	private String reportCode;//等级关系代码
	@AttrName(name="帮助")
	@Column(length=400)
	private String helpContent;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Fun parent;
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	private List<Fun> children=new ArrayList<Fun>();
	
	public void addChild(Fun child) {
		this.children.add(child);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Fun> getChildren() {
		return children;
	}
	public void setChildren(List<Fun> children) {
		this.children = children;
	}

	public Fun getParent() {
		return parent;
	}

	public void setParent(Fun parent) {
		this.parent = parent;
	}

	public String getHelpContent() {
		return helpContent;
	}

	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}

}
