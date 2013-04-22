package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mawujun.annotation.AttrName;
import com.mawujun.fun.Fun;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_menu")
public class Menu extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=20)
	private String code;//帮助记码
	@AttrName(name="名称")
	@Column(length=20)
	private String text;
	@AttrName(name="插件地址")
	@Column(length=80)
	private String pluginUrl;
	@AttrName(name="代码")
	@Column(length=1000)
	private String scripts;
	@AttrName(name="icon")
	@Column(length=40)
	private String iconCls;
	@Column(length=40)
	private String reportCode;//等级关系代码
	
	@OneToOne
	private Fun fun;
	@ManyToOne
	private Menu parent;
	@OneToMany(mappedBy="parent")
	private List<Menu> children=new ArrayList<Menu>();
	
	public void addChild(Menu child) {
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

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
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

}
