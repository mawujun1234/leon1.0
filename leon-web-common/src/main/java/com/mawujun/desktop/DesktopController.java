package com.mawujun.desktop;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuItemVO;
import com.mawujun.user.login.SwitchUserFilterImpl;
import com.mawujun.user.login.SwitchUserGrantedAuthorityImpl;
import com.mawujun.user.login.UserDetailsImpl;

@Controller
//@RequestMapping("/app")
public class DesktopController {
	@Autowired
	private MenuItemService menuItemService;
	@Resource
	private DesktopConfigService desktopConfigService;
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/desktop/query")
	@ResponseBody
	public DesktopConfig query(){	
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		
		String userId=((UserDetailsImpl)currentAuth.getPrincipal()).getId();
		DesktopConfig desktopConfig=desktopConfigService.get(userId);
		if(desktopConfig==null){
			desktopConfig=new DesktopConfig();
		}
		
		String menuId="default";
		List<MenuItemVO> menuItems=menuItemService.query4Desktop(menuId);
		//DesktopConfig config=new DesktopConfig();
		desktopConfig.setMenuItems(menuItems);
		
		
		Collection<? extends GrantedAuthority> authorities = currentAuth.getAuthorities();

		for (GrantedAuthority auth : authorities) {
			// check for switch user type of authority
			if (auth instanceof SwitchUserGrantedAuthorityImpl) {
				desktopConfig.addSwitchUser(auth.getAuthority().replaceFirst(SwitchUserFilterImpl.ROLE_PREVIOUS_ADMINISTRATOR+"_", ""));
			}
		}
		
		
		
		desktopConfig.setAuthMsg(currentAuth.getName());
		
		JsonConfigHolder.setWriteMapNullValue(false);
		return desktopConfig;
	}
	
	@RequestMapping("/desktop/queryMenuItem")
	@ResponseBody
	public MenuItemVO queryMenuItem(String jspUrl){	
		String menuId="default";
		MenuItemVO vo=menuItemService.queryMenuItem(jspUrl, menuId);
		if(vo==null){
			throw new BussinessException("提供的jsp路径有问题，不能确定菜单项!");
		}
		vo.setUrl(jspUrl);
		return vo;
		
	}

	@RequestMapping("/desktop/createOrUpdate")
	@ResponseBody
	public DesktopConfig createOrUpdate(DesktopConfig desktopConfig){
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		desktopConfig.setId(((UserDetailsImpl)currentAuth.getPrincipal()).getId());
		desktopConfigService.createOrUpdate(desktopConfig);
		return desktopConfig;
		
	}	
	
}
