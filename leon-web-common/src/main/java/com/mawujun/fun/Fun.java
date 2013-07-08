package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.StringUtils;

import com.mawujun.annotation.Label;
import com.mawujun.exten.TreeNode;

@Entity
@Table(name="leon_Fun")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class Fun extends TreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label(name="助记码")
	@Column(length=20)
	@Size(max=20)
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
	
	@Transient
	private String roleNames;
	
	@Embedded
	@AttributeOverrides( {
         @AttributeOverride(name="code", column = @Column(name="bussinessType") )
	})
	private BussinessType bussinessType;//业务类型
	
	@Enumerated(EnumType.STRING)
	private FunEnum funEnum;//是模块还是功能
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Fun parent;
	
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
	private List<Fun> children=new ArrayList<Fun>();
	
//	@OneToMany(mappedBy="fun",fetch=FetchType.LAZY)
//	private List<RoleFunAssociation> roleFunAssociations;
	
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

	/**
	 * 获取所有的父级
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 */
	public List<Fun> findAncestors() {
		List<Fun> pcategory=new ArrayList<Fun>();
		if(this.getParent()!=null){
			pcategory.add(this.getParent());
			pcategory.addAll(this.getParent().findAncestors());
		}
		return pcategory;
	}
	public List<Fun> findSiblings() {
		List<Fun> children=new ArrayList<Fun>();
		List<Fun> allCHild=this.getParent().getChildren();
		for(Fun fun:allCHild){
			if(fun.getId().equals(this.getId())){
				continue;
			} else {
				children.add(fun);
			}
		}
		return children;
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
		if(StringUtils.hasText(funEnum)){
			this.funEnum = FunEnum.valueOf(funEnum);
		}		
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
	public boolean isFun() {
		if(FunEnum.module==funEnum){
			return false;
		} else if(FunEnum.fun==funEnum){
			return true;
		}
		return false;
	}
	public BussinessType getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(BussinessType bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void addRoleName(String roleNames) {
		if(this.roleNames==null){
			this.roleNames="";
			this.roleNames+=roleNames;
		} else {
			this.roleNames+=","+roleNames;
		}
		
	}

}
