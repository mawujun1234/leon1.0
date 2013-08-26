package com.mawujun.menu;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mawujun.user.User;

/**
 * 扩展菜单的例子
 * @author mawujun 16064988@qq.com  
 *
 */
public class MenuVOExtenSample implements MenuVOExten{

	@Override
	public void execute(MenuItemVO menuItemVO, JdbcTemplate jdbcTemplate, User user) {
		// TODO Auto-generated method stub
		
		//修改名称例子
		//menuItemVO.setText("1111111");
		
		//添加子节点实例
		MenuItemVO child=new MenuItemVO();
		child.setId("111");
		child.setCode("111");
		child.setText("111");
		menuItemVO.addItems(child);
	}

}
