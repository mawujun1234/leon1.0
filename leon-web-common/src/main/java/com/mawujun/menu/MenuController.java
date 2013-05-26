package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.fun.Fun;

@Controller
@Transactional(propagation=Propagation.REQUIRED)
public class MenuController {

	@Autowired
	private MenuService menuService;
	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/menu/queryAll1")
	@ResponseBody
	public List<MenuItem> queryAll1(){		
		try {
			menuService.get("1");
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		
		//"".split(regex)
		List<MenuItem> list=getQueryResult();
		//System.out.println("=============================="+list.size());
		return list;
	}
	
	private List<MenuItem> getQueryResult(){
		List<MenuItem> list=new ArrayList<MenuItem>();
		MenuItem menuMenu=new MenuItem();
		menuMenu.setId("11111");
		menuMenu.setText("功能管理");
		Fun fun=new Fun();
		fun.setUrl("/desktop/fun/FunApp.jsp");
		//fun.setUrl("http://www.baidu.com");
		menuMenu.setFun(fun);
		list.add(menuMenu);
		
		MenuItem menuMenu1=new MenuItem();
		menuMenu1.setId("2222");
		menuMenu1.setText("菜单管理");
		Fun fun1=new Fun();
		fun1.setUrl("/desktop/menu/MenuApp.jsp");
		//fun.setUrl("http://www.baidu.com");
		menuMenu1.setFun(fun1);
		list.add(menuMenu1);
		
		for(int i=0;i<2;i++){
			MenuItem menu=new MenuItem();
			menu.setId(i+"");
			menu.setText(i+"菜单");
			for(int j=0;j<2;j++){
				MenuItem child=new MenuItem();
				child.setId(i+"");
				child.setText(i+"的子菜单"+j);
				for(int m=0;m<1;m++){
					MenuItem child1=new MenuItem();
					child1.setId(m+"");
					child1.setText(m+"的子子菜单"+j);
					child.addChild(child1);
				}
				menu.addChild(child);
			}
			list.add(menu);
		}
		return list;
	}

	
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/menu/query")
	@ResponseBody
	public List<Menu> query(){		
		return menuService.queryAll();
	}
	@RequestMapping("/menu/load")
	@ResponseBody
	public Menu load(String id){		
		return menuService.get(id);
	}
	
	@RequestMapping("/menu/create")
	@ResponseBody
	public Menu create(@RequestBody Menu menu){		
		menuService.create(menu);
		return menu;
	}
	
	@RequestMapping("/menu/update")
	@ResponseBody
	public Menu update(@RequestBody Menu menu){		
		 menuService.update(menu);
		 return menu;
	}
	
	@RequestMapping("/menu/destroy")
	@ResponseBody
	public Menu destroy(@RequestBody Menu menu){		
		menuService.delete(menu);
		return menu;
	}

}
