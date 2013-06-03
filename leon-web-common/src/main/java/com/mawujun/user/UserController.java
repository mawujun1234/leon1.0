package com.mawujun.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.mawujun.utils.page.WhereInfo;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
@Transactional
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/user/query")
	@ResponseBody
	public ModelMap query(String id){
		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
		List<User> users=userService.query(whereinfo);
		//System.out.println("==================结果输出来了"+users.size());
		ModelMap map=new ModelMap();
		map.put("root", users);
		//map.put("filterPropertys", "checked");
		return map;
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/user/queryAll")
	@ResponseBody
	public List<User> queryAll(){		
		return userService.queryAll();
	}
	@RequestMapping("/user/load")
	@ResponseBody
	public User load(String id){		
		return userService.get(id);
	}
	
	@RequestMapping("/user/create")
	@ResponseBody
	public User create(@RequestBody User user){		
		userService.create(user);
		return user;
	}
	
	@RequestMapping("/user/update")
	@ResponseBody
	public User update(@RequestBody User user){		
		 userService.update(user);
		 return user;
	}
	
	@RequestMapping("/user/destroy")
	@ResponseBody
	public User destroy(@RequestBody User user){		
		userService.delete(user);
		return user;
	}

}
