package com.mawujun.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
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
	public String checkchangeStore(String user_id,String store_id,Boolean checked,String type) {
		userService.checkchangeStore(user_id, store_id,checked, type);
		return "success";

	}
	
	@RequestMapping("/user/selectAllCheckedStore.do")
	@ResponseBody
	public List<UserStore> selectAllCheckedStore(String user_id) {
		return userService.selectAllCheckedStore(user_id);
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public String logIn(HttpServletRequest request,String username,String password){
		//System.out.println(username);
		Subject subject = SecurityUtils.getSubject(); 
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password); 
		//subject.login(token);
		String error=null;
		try {  
            subject.login(token);  
        } catch (UnknownAccountException e) {  
            error = "用户名/密码错误";  
        } catch (IncorrectCredentialsException e) {  
            error = "用户名/密码错误";  
        } catch (AuthenticationException e) {  
            //其他错误，比如锁定，如果想单独处理请单独catch处理  
            error = "其他错误：" + e.getMessage();  
        }  
        if(error != null) {//出错了，返回登录页面  
            //req.setAttribute("error", error);  
            //req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);  
        	JsonConfigHolder.setErrorsValue(error);
        	JsonConfigHolder.setSuccessValue(false);
        	return error;
        } else {//登录成功  
            //req.getRequestDispatcher("/WEB-INF/jsp/loginSuccess.jsp").forward(req, resp); 
        	 String successUrl = null;
        	 SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
             if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                 successUrl = savedRequest.getRequestUrl();
             }
             if(successUrl==null){
            	 successUrl="/index.jsp";
             }
        	return successUrl;
        }  
		
	}
	@RequestMapping("/logout.do")
	//@ResponseBody
	public String logout(String username,String password){
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		return "login";
	}
}
