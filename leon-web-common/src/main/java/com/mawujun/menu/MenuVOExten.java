package com.mawujun.menu;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mawujun.user.User;

/**
 * 对菜单进行扩展，在后台修改菜单的内容
 * @author mawujun 16064988@qq.com  
 *
 */
public interface MenuVOExten {
	/**
	 * 更新已有的菜单信息，例如菜单显示 邮件的数量等等,
	 * 还可以新增该菜单的子菜单
	 * 添加子节点的时候，子MenuItemVO的id必须设置
	 * @author mawujun 16064988@qq.com
	 */
	public void execute(MenuItemVO menuItemVO,JdbcTemplate jdbcTemplate,User user);
}
