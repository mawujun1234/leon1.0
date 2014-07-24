// Source File Name:   LoginAction.java
package com.mawujun.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;

@Controller
public class LoginAction 
{   
	@RequestMapping("/login.do")
	@ResponseBody
	public String logIn(String username,String password){
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
        	return "success";
        }  
		
	}
	@RequestMapping("/logout.do")
	//@ResponseBody
	public String logout(String username,String password){
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		return "login";
	}
//	private static final String SUCCESS="success";
//	private static final String ERROR="error";
//	//基本参数
//	private String jsonString;
//	private String loginMsg;
//	private HttpServletRequest request;
//    //表单参数
//    private String userName;
//    private String passWord;
//    private String validateCode;
//	 
//    public String LogOut(){
//    	HttpSession session=request.getSession();
//    	session.removeAttribute("loginUser");
//    	session.removeAttribute("rand");
//    	return SUCCESS;
//    }
//    
//    public String LogIn(){
//        HttpSession session=request.getSession();
//        String randCode=(String) session.getAttribute("rand");
//        if(null==randCode){
//          jsonString="{success:false,loginMsg:'错误：验证码过期，请重新获得验证码！'}";
//          return SUCCESS;
//        }
//        if(null==validateCode||!validateCode.equals(randCode)){
//          jsonString="{success:false,loginMsg:'错误：错误：输入验证码不匹配！'}";
//          return SUCCESS;
//        }
//        passWord = MD5Provider.getMD5(passWord);
//        if(userName.equals("admin")&&passWord.equals(MD5Provider.getMD5("admin"))){
//      	  UserBean user =new UserBean();
//      	  user.setUserName("username");
//      	  user.setConfirm("3");
//      	  request.getSession().setAttribute("loginUser", user);
//        }else{
//      	  loginMsg="错误：用户名密码输入有误！";
//      	  return ERROR;
//        }
//        return SUCCESS;
//    }
//    
//    public String execute(){
//      HttpSession session=request.getSession();
//      String randCode=(String) session.getAttribute("rand");
//      if(null==randCode){
//    	  loginMsg="错误：验证码过期，请重新获得验证码！";
//    	  return ERROR;
//      }
//      if(null==validateCode||!validateCode.equals(randCode)){
//    	  loginMsg="错误：输入验证码不匹配！";
//    	  return ERROR;
//      }
//      passWord = MD5Provider.getMD5(passWord);
//      if(userName.equals("admin")&&passWord.equals(MD5Provider.getMD5("admin"))){
//    	  UserBean user =new UserBean();
//    	  user.setUserName("username");
//    	  user.setConfirm("3");
//    	  request.getSession().setAttribute("loginUser", user);
//      }else{
//    	  loginMsg="错误：用户名密码输入有误！";
//    	  return ERROR;
//      }
//      return SUCCESS;
//    }
//    
//    //基本参数 
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//	public String getJsonString() {
//		return jsonString;
//	}
//
//	public String getLoginMsg() {
//		return loginMsg;
//	}
//
//	//表单参数
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//  
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setPassWord(String passWord) {
//		this.passWord = passWord;
//	}
//
//	public void setValidateCode(String validateCode) {
//		this.validateCode = validateCode;
//	}
}