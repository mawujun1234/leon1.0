package com.mawujun.user.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


public class ContentNavigationAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {
	
	public ContentNavigationAuthenticationSuccessHandler(String targetUrl){
		super(targetUrl);
	}
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
	   if(HeaderAcceptUtils.isAcceptJson(request)){
		   //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
		   //response.setStatus(402);//Unauthorized c
		   
		   response.setCharacterEncoding("UTF-8");
		   //认证的时候，用户名或密码错误时，返回的信息
		   Writer writer=response.getWriter();
			writer.write("{success:true,msg:'成功',targetUrl:'"+super.getDefaultTargetUrl()+"'}");
			writer.close();
		   return;
	   }
	   super.onAuthenticationSuccess(request, response, authentication);
   }

}
