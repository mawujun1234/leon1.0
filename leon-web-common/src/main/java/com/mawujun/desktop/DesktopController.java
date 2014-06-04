package com.mawujun.desktop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import com.mawujun.utils.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.menu.MenuItem;
import com.mawujun.menu.MenuItemService;
import com.mawujun.menu.MenuItemVO;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.login.SwitchUserFilterImpl;
import com.mawujun.user.login.SwitchUserGrantedAuthorityImpl;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.DefaultValues;
import com.mawujun.utils.M;
import com.mawujun.utils.P;
import com.mawujun.utils.ParameterHolder;

@Controller
//@RequestMapping("/app")
public class DesktopController {
	@Autowired
	private MenuItemService menuItemService;
	@Resource
	private DesktopConfigService desktopConfigService;
	@Resource
	private QuickStartServcie quickStartServcie;
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/desktop/query")
	@ResponseBody
	public DesktopConfig query(){	
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail=(UserDetailsImpl)currentAuth.getPrincipal();
		
		String userId=userDetail.getId();
		DesktopConfig desktopConfig=desktopConfigService.get(userId);
		if(desktopConfig==null) {
			desktopConfig=new DesktopConfig();
		} //else {
			//获取快捷方式
			//M.QuickStart.Id.
			//List<QuickStart> quickStarts=quickStartServcie.query(Cnd.select().andEquals("id.userId", userId));
			List<QuickStart> quickStarts=quickStartServcie.query(Cnd.select().andEquals(M.QuickStart.id.userId, userId));
			for(QuickStart quickStart:quickStarts){
				//快速启动的数据是放在，一登陆就渲染呢？还是等点击的时候延迟加载。
				MenuItem leaf=menuItemService.get(quickStart.getId().getMenuItemId());
				if(leaf!=null){
					MenuItemVO vo=BeanUtils.copyOrCast(leaf, MenuItemVO.class);
					vo.setFunId(leaf.getFun()!=null?leaf.getFun().getId():null);
					desktopConfig.addQuickstart(vo);
				}
			}
			
		//}
		
		
		//组装菜单
		List<MenuItemVO> menuItems=menuItemService.query4Desktop(userDetail.getMenuId(),userDetail.isAdmin(),null,userDetail.getId());
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
		//String menuId="default";
		
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail=(UserDetailsImpl)currentAuth.getPrincipal();
		
		String menuId=ParameterHolder.getUserParameterValue(userDetail.getId(), P.menuId);
		if(menuId==null){
			menuId=DefaultValues.Menu_id;
		}
		MenuItemVO vo=menuItemService.queryMenuItem(jspUrl, menuId);
		if(vo==null){
			throw new BusinessException("提供的jsp路径有问题，不能确定菜单项!");
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
	
	@RequestMapping("/desktop/createQuickstart")
	@ResponseBody
	public QuickStart createQuickstart(String menuItemId){
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl detail=(UserDetailsImpl)currentAuth.getPrincipal();
		//为用户添加一个快捷方式
		//desktopConfigService.createQuickstart(menuItemId, detail.getId());
		
		QuickStart quickStart=new QuickStart();
		quickStart.setId(QuickStart.Id.getInstance(detail.getId(), menuItemId));
		quickStartServcie.create(quickStart);
		return quickStart;
		
	}	
	@RequestMapping("/desktop/queryQuickstarts")
	@ResponseBody
	public List<MenuItemVO> queryQuickstarts(){
		List<QuickStart> quickStarts=quickStartServcie.query(Cnd.select().andEquals(M.QuickStart.id.userId, SecurityContextHolder.getUserId()));
		
		List<MenuItemVO> list=new ArrayList<MenuItemVO>();
		for(QuickStart quickStart:quickStarts){
			
			MenuItem leaf=menuItemService.get(quickStart.getId().getMenuItemId());
			if(leaf!=null){
				MenuItemVO vo=BeanUtils.copyOrCast(leaf, MenuItemVO.class);
				vo.setFunId(leaf.getFun()!=null?leaf.getFun().getId():null);
				list.add(vo);
			}
		}
		return list;
		
	}	
}
