package com.mawujun.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.fun.Fun;

@Controller
//@Transactional(propagation=Propagation.REQUIRED)
public class MenuController {

	@Autowired
	private MenuService menuService;
	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/menu/queryAll")
	@ResponseBody
	public List<Menu> queryAll(){		
		try {
			menuService.get1("1");
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
		
		//"".split(regex)
		List<Menu> list=getQueryResult();
		//System.out.println("=============================="+list.size());
		return list;
	}
	
	private List<Menu> getQueryResult(){
		List<Menu> list=new ArrayList<Menu>();
		Menu menuFun=new Menu();
		menuFun.setId("11111");
		menuFun.setText("功能管理");
		Fun fun=new Fun();
		fun.setUrl("/desktop/fun/FunApp.jsp");
		//fun.setUrl("http://www.baidu.com");
		menuFun.setFun(fun);
		list.add(menuFun);
		
		for(int i=0;i<2;i++){
			Menu menu=new Menu();
			menu.setId(i+"");
			menu.setText(i+"菜单");
			for(int j=0;j<2;j++){
				Menu child=new Menu();
				child.setId(i+"");
				child.setText(i+"的子菜单"+j);
				for(int m=0;m<1;m++){
					Menu child1=new Menu();
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


}
