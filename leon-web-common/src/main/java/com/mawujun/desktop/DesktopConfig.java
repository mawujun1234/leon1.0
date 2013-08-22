package com.mawujun.desktop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mawujun.menu.MenuItemVO;

public class DesktopConfig {
	private Set<String> switchUsers;
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
}
