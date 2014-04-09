package com.mawujun.menu;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.JsonConfigHolder;
import com.mawujun.icon.IconUtils;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.WhereInfo;

@Controller
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
		//WhereInfo whereinfo=WhereInfo.parse("parent.id", "root".equals(id)?null:id);
		//WhereInfo menuIdwhereinfo=WhereInfo.parse("menu.id", menuId);
		//List<MenuItem> menuItems=menuItemService.query(whereinfo,menuIdwhereinfo);
		
		if(menuId==null){
			menuId="default";
		}
		List<MenuItem> menuItems=menuItemService.query(Cnd.select().andEquals("menu.id", menuId).andEquals("parent.id", "root".equals(id)?null:id));

		JsonConfigHolder.setFilterPropertys("children,parent,menu",MenuItem.class);
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
		//WhereInfo menuIdwhereinfo=WhereInfo.parse("menu.id", menuId);
		//List<MenuItem> funes=menuItemService.query(menuIdwhereinfo);
		List<MenuItem> funes=menuItemService.query(Cnd.select().andEquals("menu.id", menuId));
		return funes;
	}
	@RequestMapping("/menuItem/load")
	@ResponseBody
	public MenuItem load(String id){		
		return menuItemService.get(id);
	}
	
	@RequestMapping("/menuItem/create")
	@ResponseBody
	public MenuItem create(HttpServletRequest request,@RequestBody MenuItem menuItem,String menuId) throws IOException{		
		menuItem.setMenu(new Menu(menuId));
		menuItemService.create(menuItem);
		appenPngCls(request,menuItem.getIconCls(),menuItem.getIconCls32());
		JsonConfigHolder.setFilterPropertys("children",MenuItem.class);
		return menuItem;
	}
	
	@RequestMapping("/menuItem/createByFun")
	@ResponseBody
	public MenuItem create(String funId,String parentId,String menuId){		
		
		MenuItem menuItem=menuItemService.create(funId,parentId,menuId);
		JsonConfigHolder.setFilterPropertys("children",MenuItem.class);
		return menuItem;
	}
	
	@RequestMapping("/menuItem/update")
	@ResponseBody
	public MenuItem update(HttpServletRequest request,@RequestBody MenuItem menuItem) throws IOException{	
		appenPngCls(request,menuItem.getIconCls(),menuItem.getIconCls32());//顺序很重要
		
		menuItemService.update(menuItem);
		
		JsonConfigHolder.setFilterPropertys("children",MenuItem.class);
		 return menuItem;
	}
	
	@RequestMapping("/menuItem/destroy")
	@ResponseBody
	public MenuItem destroy(@RequestBody MenuItem menuItem){		
		menuItemService.delete(menuItem);
		JsonConfigHolder.setFilterPropertys("children",MenuItem.class);
		return menuItem;
	}
	/**
	 * 根据选择了的png，动态往pngs.css文件中添加css的class，这样可以减少pngs文件的大小
	 * @author mawujun 16064988@qq.com
	 * @throws IOException 
	 */
	public void appenPngCls(HttpServletRequest request,String iconCls,String iconCls32) throws IOException{
		long count=menuItemService.queryCount(Cnd.select().andEquals("iconCls", iconCls));
		
		if(count==0){
			String cssPath=request.getSession().getServletContext().getRealPath("/common/pngs.css");
			IconUtils.generatorPngCss(cssPath, iconCls,iconCls32);
		}
	}

}
