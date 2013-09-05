package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.mawujun.annotation.Label;
import com.mawujun.fun.Fun;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_menuItem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class MenuItem extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=20)
	private String code;//帮助记码
	@Label(name="名称")
	@Column(length=20)
	private String text;
	@Label(name="java扩展")
	@Column(length=80)
	private String javaClass;
	@Label(name="代码")
	@Lob 
	@Basic(fetch = FetchType.EAGER) 
	@Column(columnDefinition="CLOB", nullable=true) 
	private String scripts;
	@Label(name="图标")
	@Column(length=40)
	private String iconCls="menu_category";
	@Column(length=40)
	private String reportCode;//等级关系代码
	
	private Boolean leaf=false;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@NotNull
	private Menu menu;
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	private Fun fun;
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	private MenuItem parent;
//	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
//	private List<MenuItem> children=new ArrayList<MenuItem>();
//	
//	public void addChild(MenuItem child) {
//		this.children.add(child);
//	}
	
	public String getUrl() {
		if(this.getFun()!=null){
			return this.getFun().getUrl();
		}
		return null;
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

//	public List<MenuItem> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<MenuItem> children) {
//		this.children = children;
//	}

	public Fun getFun() {
		return fun;
	}

	public void setFun(Fun fun) {
		this.fun = fun;
	}
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public MenuItem getParent() {
		return parent;
	}

	public void setParent(MenuItem parent) {
		this.parent = parent;
	}
	public List<MenuItem> findAncestors() {
		List<MenuItem> pcategory=new ArrayList<MenuItem>();
		if(this.getParent()!=null){
			pcategory.add(this.getParent());
			pcategory.addAll(this.getParent().findAncestors());
		}
		return pcategory;
	}
	public Boolean isLeaf() {
//		if(this.children==null || this.children.size()==0){
//			return true;
//		}
//		return false;
		return leaf;
	}


	public Boolean getLeaf() {
		return leaf;
	}


	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
//	public boolean isAutoCreate(){
//		if(this.getFun()==null){
//			return false;
//		} else {
//			if(this.getId().equals(this.getFun().getMenuItemId())){
//				return true;
//			} else {
//				return false;
//			}
//		}
//	}

}
