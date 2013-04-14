package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.fun.Fun;

@Controller
public class FunController {

	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/fun/list")
	@ResponseBody
	public List<Fun> list(){		
		//"".split(regex)
		List<Fun> list=getQueryResult();
		System.out.println("=============================="+list.size());
		return list;
	}
	
	private List<Fun> getQueryResult(){
		List<Fun> list=new ArrayList<Fun>();

		for(int i=0;i<2;i++){
			Fun menu=new Fun();
			menu.setId(i+"");
			menu.setText(i+"菜单");
			for(int j=0;j<2;j++){
				Fun child=new Fun();
				child.setId(i+"");
				child.setText(i+"的子菜单"+j);
				for(int m=0;m<1;m++){
					Fun child1=new Fun();
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
