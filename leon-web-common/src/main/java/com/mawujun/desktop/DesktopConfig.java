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
	private String wallpaper;
	private boolean wallpaperStretch=false;
	@Transient
	private String authMsg;//认证用户逇信息
	@Transient
	private Set<String> switchUsers;
	@Transient
	private List<MenuItemVO> menuItems;
	
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

}
