package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MenuController {

	/**
	 * 一次性把菜单的所有数据都返回到前台
	 * @return
	 */
	@RequestMapping("/menu/list")
	@ResponseBody
	public List<Menu> list(){		
		List<Menu> list=getQueryResult();
		System.out.println("=============================="+list.size());
		return list;
	}
	
	private List<Menu> getQueryResult(){
		List<Menu> list=new ArrayList<Menu>();
		for(int i=0;i<10;i++){
			Menu menu=new Menu();
			menu.setId(i+"");
			menu.setText(i+"菜单");
			for(int j=0;j<10;j++){
				Menu child=new Menu();
				child.setId(i+"");
				child.setText(i+"的子菜单"+j);
				menu.addChild(child);
			}
			list.add(menu);
		}
		return list;
	}


}
