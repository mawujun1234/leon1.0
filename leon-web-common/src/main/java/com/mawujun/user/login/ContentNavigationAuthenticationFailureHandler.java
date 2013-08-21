package com.mawujun.user.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * 根据请求的内容来返回，是重定向到jsp，还是返回json数据表示没有成功登录
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class ContentNavigationAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException exception) throws IOException, ServletException {
		   if(HeaderAcceptUtils.isAcceptJson(request)){
			   //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
			   //response.setStatus(402);//Unauthorized 
			   //认证的时候，用户名或密码错误时，返回的信息
			   Writer writer=response.getWriter();
				writer.write("{success:false,msg:'"+exception.getMessage()+"!'}");
				writer.close();
			   return;
		   }
		   super.onAuthenticationFailure(request, response, exception);
	   }

}
