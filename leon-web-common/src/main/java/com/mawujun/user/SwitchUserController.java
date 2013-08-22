package com.mawujun.user;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.page.QueryResult;

/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/switchUser")
public class SwitchUserController {

	@Resource
	private SwitchUserService switchUserService;
	
	@Autowired
	UserController userController;
	/**
	 * 查询可以切换的用户的范围
	 * @author mawujun 16064988@qq.com 
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public QueryResult<User> querySwitchUsers(Integer start,Integer limit,String userName,String masterId){
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		if(((UserDetailsImpl)currentAuth.getPrincipal()).isAdmin()){
			return userController.query(start, limit, userName);
		} else {
			return null;
		}
		
	}
//
//	@RequestMapping("/query")
//	@ResponseBody
//	public List<SwitchUser> query() {	
//		List<SwitchUser> switchUseres=switchUserService.queryAll();
//		return switchUseres;
//	}

	@RequestMapping("/load")
	public SwitchUser load(String id) {
		return switchUserService.get(id);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public SwitchUser create(@RequestBody SwitchUser switchUser) {
		switchUserService.create(switchUser);
		return switchUser;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public  SwitchUser update(@RequestBody SwitchUser switchUser) {
		switchUserService.update(switchUser);
		return switchUser;
	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public String destroy(String id) {
		switchUserService.delete(id);
		return id;
	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public SwitchUser destroy(@RequestBody SwitchUser switchUser) {
		switchUserService.delete(switchUser);
		return switchUser;
	}
	
	
}
