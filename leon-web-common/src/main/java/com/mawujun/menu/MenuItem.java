package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import com.mawujun.annotation.Label;
import com.mawujun.fun.Fun;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_menuItem")
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
	@Label(name="插件地址")
	@Column(length=80)
	private String pluginUrl;
	@Label(name="代码")
	@Column(length=1000)
	private String scripts;
	@Label(name="图标")
	@Column(length=40)
	private String iconCls;
	@Column(length=40)
	private String reportCode;//等级关系代码
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Menu menu;
	@ManyToOne(fetch=FetchType.EAGER,optional=true)
	private Fun fun;
	@ManyToOne(fetch=FetchType.LAZY)
	private MenuItem parent;
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	private List<MenuItem> children=new ArrayList<MenuItem>();
	
	public void addChild(MenuItem child) {
		this.children.add(child);
	}
	
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

	public String getPluginUrl() {
		return pluginUrl;
	}

	public void setPluginUrl(String pluginUrl) {
		this.pluginUrl = pluginUrl;
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

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

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

}
