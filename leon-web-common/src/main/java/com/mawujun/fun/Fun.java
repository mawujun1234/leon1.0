package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mawujun.annotation.Label;
import com.mawujun.exten.TreeNode;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_Fun")
public class Fun extends TreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label(name="助记码")
	@Column(length=20)
	private String code;//助记码
	@Column(length=20)
	@Label(name="名称")
	private String text;
	@Column(length=80)
	@Label(name="地址")
	private String url;
	//private String iconCls;
	@Column(length=40)
	private String reportCode;//等级关系代码
	@Label(name="帮助")
	@Column(length=100)
	private String helpContent;//存放的是html内容的地址
	
	@Enumerated(EnumType.STRING)
	private FunEnum funEnum;//是模块还是功能
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Fun parent;
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	private List<Fun> children=new ArrayList<Fun>();
	
	public Fun(){
		super();
	}
	public Fun(String id){
		super();
		this.id=id;
	}
	
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
	public FunEnum getFunEnum() {
		return funEnum;
	}
	public void setFunEnum(String funEnum) {
		this.funEnum = FunEnum.valueOf(funEnum);
	}
	public void setFunEnum(FunEnum funEnum) {
		this.funEnum = funEnum;
	}
	
	public String getIconCls() {
		//System.out.println(funEnum);
		if(FunEnum.module==funEnum){
			return "fun-module-iconCls";
		} else if(FunEnum.fun==funEnum){
			return "fun-fun-iconCls";
		}
		return null;
	}
	public boolean isLeaf() {
		 if(FunEnum.fun==funEnum){
			return true;
		}
		 return false;
	}

}
