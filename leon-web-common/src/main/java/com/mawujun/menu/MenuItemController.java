package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.ToJsonConfigHolder;
import com.mawujun.fun.Fun;
import com.mawujun.repository.hibernate.HibernateUtils;
import com.mawujun.utils.page.WhereInfo;

@Controller
@Transactional(propagation=Propagation.REQUIRED)
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;

	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/menuItem/query")
	@ResponseBody
	public List<MenuItem> query(String id,String menuId){
		WhereInfo whereinfo=WhereInfo.parse("parent.id", "root".equals(id)?null:id);
		WhereInfo menuIdwhereinfo=WhereInfo.parse("menu.id", menuId);
		List<MenuItem> menuItems=menuItemService.query(whereinfo,menuIdwhereinfo);

		return menuItems;
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/menuItem/query4Desktop")
	@ResponseBody
	public List<MenuItemVO> query4Desktop(String menuId){		

		List<MenuItemVO> menuItems=menuItemService.query4Desktop(menuId);
		ToJsonConfigHolder.setWriteMapNullValue(false);
		return menuItems;
	}
	
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/menuItem/queryAll")
	@ResponseBody
	public List<MenuItem> queryAll(String menuId){		
		//这里没有进行上下级的组装，所以界面上出现了3个菜单
		WhereInfo menuIdwhereinfo=WhereInfo.parse("menu.id", menuId);
		List<MenuItem> funes=menuItemService.query(menuIdwhereinfo);
		return funes;
	}
	@RequestMapping("/menuItem/load")
	@ResponseBody
	public MenuItem load(String id){		
		return menuItemService.get(id);
	}
	
	@RequestMapping("/menuItem/create")
	@ResponseBody
	public MenuItem create(@RequestBody MenuItem menuItem,String menuId){		
		menuItem.setMenu(new Menu(menuId));
		menuItemService.create(menuItem);
		return menuItem;
	}
	
	@RequestMapping("/menuItem/createByFun")
	@ResponseBody
	public MenuItem create(String funId,String parentId){		
		
		MenuItem menuItem=menuItemService.create(funId,parentId);
		return menuItem;
	}
	
	@RequestMapping("/menuItem/update")
	@ResponseBody
	public MenuItem update(@RequestBody MenuItem menuItem){		
		menuItemService.update(menuItem);
		 return menuItem;
	}
	
	@RequestMapping("/menuItem/destroy")
	@ResponseBody
	public MenuItem destroy(@RequestBody MenuItem menuItem){		
		menuItemService.delete(menuItem);
		return menuItem;
	}

}
