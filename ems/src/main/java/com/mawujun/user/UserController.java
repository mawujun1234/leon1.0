package com.mawujun.user;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//@Autowired
	//private UserRoleService userRoleService;

	//@Autowired
	//private PasswordEncoder passwordEncoder;
//	@Resource(name="service.user")
//	UserServiceImpl userServiceImpl;
	
	@RequestMapping("/user/list.do")
	@ResponseBody
	public Page list(Integer start,Integer limit,String name ) {
		Page page=Page.getInstance(start, limit).addParam(M.User.name, StringUtils.hasText(name)?"%"+name+"%":null);
		return userService.queryPage(page);
	}
	@RequestMapping("/user/listUserByFunRole.do")
	@ResponseBody
	public List<User> listUserByFunRole(String funrole_id){
		return userService.listUserByFunRole(funrole_id);
	}

	@RequestMapping("/user/save.do")
	@ResponseBody
	public String save(User user) {
		user.setPassword("0");
		userService.save(user);
		return "success";
	}
	@RequestMapping("/user/update.do")
	@ResponseBody
	public String update(User user) {
		userService.update(user);
		return "success";
	}
	@RequestMapping("/user/delete.do")
	@ResponseBody
	public String delete(String id) {
		if("admin".equalsIgnoreCase(id)){
			throw new BusinessException("管理员账号不能删除!");
		}
		userService.deleteById(id);
		return "success";
	}
	
	@RequestMapping("/user/changePwd.do")
	@ResponseBody
	public String changePwd(String password_old,String password_new) {
		Subject subject = SecurityUtils.getSubject(); 
		//subject.getPrincipal();
		int i=userService.changePwd(subject.getPrincipal().toString(),password_old,password_new);
		if(i<=0){
			JsonConfigHolder.setSuccessValue(false);
			return "源密码不正确!";
		}
		return "success";
	}
	
	@RequestMapping("/user/listFunRole.do")
	@ResponseBody
	public List<FunRole> listFunRole(String node,String user_id) {
		if("root".equals(node)){
			node=null;
		}

		return userService.listFunRole(node,user_id);
	}
	
	@RequestMapping("/user/checkchangeFunRole.do")
	@ResponseBody
	public String checkchangeFunRole(String user_id,String funRole_id,Boolean checked) {
		if(checked){
			userService.checkedFunRole(user_id, funRole_id);
		} else {
			userService.unCheckedFunRole(user_id, funRole_id);
		}
		return "success";

	}
	
	@RequestMapping("/user/selectAllCheckedFunRole.do")
	@ResponseBody
	public List<String> selectAllCheckedFunRole(String user_id) {
		return userService.selectAllCheckedFunRole(user_id);
	}
	
	
//	///////////////////////////////////////////////
//	@RequestMapping("/user/listDataRole.do")
//	@ResponseBody
//	public List<DataRole> listDataRole(String node,String user_id) {
//		if("root".equals(node)){
//			node=null;
//		}
//
//		return userService.listDataRole(node,user_id);
//	}
//	
//	@RequestMapping("/user/checkchangeDataRole.do")
//	@ResponseBody
//	public String checkchangeDataRole(String user_id,String dataRole_id,Boolean checked) {
//		if(checked){
//			userService.checkedDataRole(user_id, dataRole_id);
//		} else {
//			userService.unCheckedDataRole(user_id, dataRole_id);
//		}
//		return "success";
//
//	}
//	
//	@RequestMapping("/user/selectAllCheckedDataRole.do")
//	@ResponseBody
//	public List<String> selectAllCheckedDataRole(String user_id) {
//		return userService.selectAllCheckedDataRole(user_id);
//	}
	
	
	@RequestMapping("/user/checkchangeStore.do")
	@ResponseBody
	public String checkchangeStore(String user_id,String store_id,Boolean checked) {
		userService.checkchangeStore(user_id, store_id, checked);
		return "success";

	}
	
	@RequestMapping("/user/selectAllCheckedStore.do")
	@ResponseBody
	public List<String> selectAllCheckedStore(String user_id) {
		return userService.selectAllCheckedStore(user_id);
	}
}
