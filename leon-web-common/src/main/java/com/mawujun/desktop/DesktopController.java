package com.mawujun.desktop;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuItemVO;
import com.mawujun.user.login.SwitchUserFilterImpl;
import com.mawujun.user.login.SwitchUserGrantedAuthorityImpl;

@Controller
public class DesktopController {
	@Autowired
	private MenuItemService menuItemService;
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/desktop/query")
	@ResponseBody
	public DesktopConfig query4Desktop(String menuId){		

		List<MenuItemVO> menuItems=menuItemService.query4Desktop(menuId);
		DesktopConfig config=new DesktopConfig();
		config.setMenuItems(menuItems);
		
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = currentAuth.getAuthorities();

		for (GrantedAuthority auth : authorities) {
			// check for switch user type of authority
			if (auth instanceof SwitchUserGrantedAuthorityImpl) {
				config.addSwitchUser(auth.getAuthority().replaceFirst(SwitchUserFilterImpl.ROLE_PREVIOUS_ADMINISTRATOR+"_", ""));
			}
		}
		
		JsonConfigHolder.setWriteMapNullValue(false);
		return config;
	}
	
}
