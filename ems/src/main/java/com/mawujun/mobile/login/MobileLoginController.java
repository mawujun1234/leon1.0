package com.mawujun.mobile.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.WorkUnitService;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.MobileUsernamePasswordToken;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.User;
import com.mawujun.utils.M;

/**
 * 主要用在移动端的
 * @author mawujun 16064988@qq.com  
 *
 */
@Controller
public class MobileLoginController {
	private static Logger logger = LogManager.getLogger(MobileLoginController.class.getName());
	@Resource
	private WorkUnitService workUnitService;
	@RequestMapping("/mobile/login.do")
	@ResponseBody
	public User logIn(HttpServletRequest request,HttpServletResponse response,String jsonpCallback,String loginName,String password,Boolean rememberMe){
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.addHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS"); 
		//response.addHeader("Access-Control-Allow-Headers", "Content-type,hello");
		//System.out.println(username);
		Subject subject = SecurityUtils.getSubject(); 
		
		MobileUsernamePasswordToken token = new MobileUsernamePasswordToken(loginName, password); 
		token.setRememberMe(rememberMe==null?false:rememberMe);
		//subject.login(token);
		
		Map<String,String> result=new HashMap<String,String>();
		
		String error=null;
		try {  
            subject.login(token);  
        } catch (UnknownAccountException e) {
        	logger.error(e);
            error = "用户名/密码错误";  
        } catch (IncorrectCredentialsException e) {  
        	logger.error(e);
            error = "用户名/密码错误";  
        } catch (AuthenticationException e) {  
        	logger.error(e);
            //其他错误，比如锁定，如果想单独处理请单独catch处理  
            error = "账号或密码错误!";  
        }  
		//JsonConfigHolder.setRootName("reasons");
		JsonConfigHolder.setJsonpCallback(jsonpCallback);
        if(error != null) {//出错了，返回登录页面  
            //req.setAttribute("error", error);  
            //req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);  
        	//JsonConfigHolder.setErrorsValue(error);
        	JsonConfigHolder.setSuccessValue(false);
        	JsonConfigHolder.addProperty("reasons",  Reason.getInstance().setReason(error));
        	
        	return new User();
        	//return Reason.getInstance().setReason(error);
        } else {//登录成功  
            //req.getRequestDispatcher("/WEB-INF/jsp/loginSuccess.jsp").forward(req, resp); 
        	 String successUrl = null;
        	 SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
             if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                 successUrl = savedRequest.getRequestUrl();
             }
             if(successUrl==null){
            	 successUrl="/message.html";
             }
             JsonConfigHolder.setDatePattern("yyyy-MM-dd hh:mm:ss");
             return ShiroUtils.getAuthenticationInfo();
        }  
		
	}
	
	@RequestMapping("/mobile/updatePassword.do")
	@ResponseBody
	public String updatePassword(String jsonpCallback,String password,String password_repeat){
		取消掉jsonpCallback，然后把www放到同个项目里好了
		JsonConfigHolder.setJsonpCallback(jsonpCallback);
		User user=ShiroUtils.getAuthenticationInfo();
		String loginName=user.getUsername();
		workUnitService.update(Cnd.update().set(M.WorkUnit.password, password).andEquals(M.WorkUnit.loginName, loginName));	
		return "success";
	}
}
