package com.mawujun.user.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.mawujun.user.User;
import com.mawujun.user.UserService;

/**
 * 添加了登录成功后更新最新登录时间和记录登录地址的功能
 * @author mawujun 16064988@qq.com  
 *
 */
public class SavedRequestAwareAuthenticationSuccessHandlerImpl extends
		SavedRequestAwareAuthenticationSuccessHandler {
	private UserService userService;
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws ServletException, IOException {
		 UserDetailsImpl userDetail=((UserDetailsImpl)authentication.getPrincipal());
		 
		 User user=userDetail.getUser();
		 user.setLastIp(request.getRemoteAddr());
		 user.setLastLoginDate(new Date());
		 userService.update(user);
		 
		 super.onAuthenticationSuccess(request, response, authentication);
	 }
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
