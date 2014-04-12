package com.mawujun.user;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.login.SwitchUserFilterImpl;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.Params;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
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
	@Resource
	private UserService userService;
	
	//@Autowired
	//UserController userController;
	/**
	 * 查询可以切换的用户的范围
	 * @author mawujun 16064988@qq.com 
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Page querySwitchUsers(Integer start,Integer limit,String userName,String masterId){
		//用在前台用户切换用户的时候
		if(!StringUtils.hasText(masterId)){
			Authentication masterAuth =SwitchUserFilterImpl.getMasterAuthentication();// SecurityContextHolder.getContext().getAuthentication();
			masterId=((UserDetailsImpl)masterAuth.getPrincipal()).getId();
		}
		return switchUserService.querySwitchUsers(start, limit, userName, masterId);
		
	}
//
//	@RequestMapping("/query")
//	@ResponseBody
//	public List<SwitchUser> query() {	
//		List<SwitchUser> switchUseres=switchUserService.queryAll();
//		return switchUseres;
//	}
//
//	@RequestMapping("/load")
//	public SwitchUser load(String id) {
//		return switchUserService.get(id);
//	}
	
	@RequestMapping("/create")
	@ResponseBody
	public SwitchUser create(String masterId,String switchUserId) {
		SwitchUser switchUser=new SwitchUser(masterId,switchUserId);
		switchUserService.create(switchUser);
		return switchUser;
	}
	
//	@RequestMapping("/update")
//	@ResponseBody
//	public  SwitchUser update(@RequestBody SwitchUser switchUser) {
//		switchUserService.update(switchUser);
//		return switchUser;
//	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public String destroy(String masterId,String switchUserId) {
		switchUserService.deleteBatch(Cnd.delete().andEquals("master.id", masterId).andEquals("switchUser.id", switchUserId));
		return "成功";
	}
	
//	@RequestMapping("/destroy")
//	@ResponseBody
//	public SwitchUser destroy(@RequestBody SwitchUser switchUser) {
//		switchUserService.delete(switchUser);
//		return switchUser;
//	}
	
	/**
	 * 判断用户是否有权限切换到另一个用户
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param masterId
	 * @return
	 */
	@RequestMapping("/checkPermission")
	@ResponseBody
	public String checkPermission(String masterId,String j_username ) {
		String msg="允许";
		Authentication masterAuth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl detail=((UserDetailsImpl) masterAuth.getPrincipal());
		if(detail.isAdmin()){
			return msg;
		}
		// 用在前台用户切换用户的时候
		if (!StringUtils.hasText(masterId)) {
			// SecurityContextHolder.getContext().getAuthentication();
			masterId = detail.getId();
		}
		//WhereInfo
		//int count=switchUserService.queryCountMybatis("querySwitchUsersCount", Params.init().add("masterId", masterId).add("j_username", j_username));
		int count=userService.querySwitchUsersCount(masterId, j_username);
		if(count>0){
			//JsonConfigHolder.setMsg("允许");
		} else {
			//JsonConfigHolder.setMsg("没有权限切换到用户"+j_username);
			//JsonConfigHolder.setSuccessValue(false);
			throw new BusinessException("没有权限切换到用户"+j_username);
		}
		return msg;
		//switchUserService.queryCount(Cnd.select().andEquals("master.id", masterId).andEquals("switchUser.id", switchUserId))
		
	}
	
	
}
