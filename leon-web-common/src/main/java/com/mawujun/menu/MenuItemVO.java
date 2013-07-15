package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuItemVO {
	private String id;
	private String code;//帮助记码
	private String text;
	private String pluginUrl;
	private String scripts;
	private String iconCls;
	private String url;
	
	private MenuVO menu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuVO getMenu() {
		return menu;
	}

	public void setMenu(MenuVO menu) {
		this.menu = menu;
	}
	public void addItems(MenuItemVO items) {
		if(this.menu==null){
			this.menu=new MenuVO();
		}
		this.menu.addItems(items);
	}
	
}
/**
 * 界面要用到的菜单,和Menu对象无任何关系
 * @author mawujun 16064988@qq.com  
 *
 */
class MenuVO{
	private List<MenuItemVO> items;

	public List<MenuItemVO> getItems() {
		return items;
	}

	public void setItems(List<MenuItemVO> items) {
		this.items = items;
	}
	
	public void addItems(MenuItemVO items) {
		if(this.items==null){
			this.items=new ArrayList<MenuItemVO>();
		}
		this.items.add(items);
	}
}
