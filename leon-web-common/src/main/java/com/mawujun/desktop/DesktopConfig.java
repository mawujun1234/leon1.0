package com.mawujun.desktop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.menu.MenuItemVO;
import com.mawujun.repository.idEntity.IdEntity;

@Entity
@Table(name="leon_DesktopConfig")
public class DesktopConfig implements IdEntity<String>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length=36)
	private String id;
	@Column(length=100)
	private String wallpaper;
	private Boolean wallpaperStretch=false;
	@Column(length=10)
	private String menubarDock;
	@Column(length=10)
	private String taskbarDock;
	@Column
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean taskbarAutoHide=false;
	@Column
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean menubarAutoHide=false;
	
	
	
	
	
	@Transient
	private String authMsg;//认证用户逇信息
	@Transient
	private Set<String> switchUsers;
	@Transient
	private List<MenuItemVO> menuItems;
	
	@Transient
	private List<MenuItemVO> quickstarts;
	
	public Set<String> getSwitchUsers() {
		return switchUsers;
	}
	public void addSwitchUser(String switchUser) {
		if(switchUsers==null){
			switchUsers=new HashSet<String>();
		}
		this.switchUsers.add(switchUser);
	}
	public List<MenuItemVO> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItemVO> menuItems) {
		this.menuItems = menuItems;
	}
	public String getAuthMsg() {
		return authMsg;
	}
	public void setAuthMsg(String authMsg) {
		this.authMsg = authMsg;
	}
	public String getWallpaper() {
		return wallpaper;
	}
	public void setWallpaper(String wallpaper) {
		this.wallpaper = wallpaper;
	}
	public boolean isWallpaperStretch() {
		return wallpaperStretch;
	}
	public void setWallpaperStretch(boolean wallpaperStretch) {
		this.wallpaperStretch = wallpaperStretch;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public String getMenubarDock() {
		return menubarDock;
	}
	public void setMenubarDock(String menubarDock) {
		this.menubarDock = menubarDock;
	}
	public String getTaskbarDock() {
		return taskbarDock;
	}
	public void setTaskbarDock(String taskbarDock) {
		this.taskbarDock = taskbarDock;
	}
	public void setSwitchUsers(Set<String> switchUsers) {
		this.switchUsers = switchUsers;
	}
	public Boolean isTaskbarAutoHide() {
		return taskbarAutoHide;
	}
	public void setTaskbarAutoHide(Boolean taskbarAutoHide) {
		if(taskbarAutoHide==null){
			this.taskbarAutoHide=false;
			return;
		}
		this.taskbarAutoHide = taskbarAutoHide;
	}
	public Boolean isMenubarAutoHide() {
		return menubarAutoHide;
	}
	public void setMenubarAutoHide(Boolean menubarAutoHide) {
		if(menubarAutoHide==null){
			this.menubarAutoHide=false;
			return;
		}
		this.menubarAutoHide = menubarAutoHide;
	}
	public List<MenuItemVO> getQuickstarts() {
		return quickstarts;
	}
	public void setQuickstarts(List<MenuItemVO> quickstarts) {
		this.quickstarts = quickstarts;
	}
	public void addQuickstart(MenuItemVO quickstart) {
		if(this.quickstarts==null){
			this.quickstarts=new ArrayList<MenuItemVO>();
		}
		this.quickstarts.add(quickstart);
	}

}
