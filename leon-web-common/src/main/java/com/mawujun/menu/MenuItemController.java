package com.mawujun.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping("/menuItem/queryChildren")
	@ResponseBody
	public ModelMap queryChildren(String node){
		WhereInfo whereinfo=WhereInfo.parse("parent.id", node);
		List<MenuItem> funes=menuItemService.query(whereinfo);
		//System.out.println("==================结果输出来了"+funes.size());
		ModelMap map=new ModelMap();
		map.put("root", funes);
		return map;
	}

	
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/menuItem/queryAll")
	@ResponseBody
	public List<MenuItem> queryAll(){		
		return menuItemService.queryAll();
	}
	@RequestMapping("/menuItem/get")
	@ResponseBody
	public MenuItem get(String id){		
		return menuItemService.get(id);
	}
	
	@RequestMapping("/menuItem/create")
	@ResponseBody
	public MenuItem create(@RequestBody MenuItem menuItem){		
		menuItemService.create(menuItem);
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
