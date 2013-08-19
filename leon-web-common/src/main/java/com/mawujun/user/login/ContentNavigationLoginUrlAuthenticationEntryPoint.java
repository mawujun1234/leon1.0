package com.mawujun.user.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
/**
 * 根据氢气的accept内容来返回不同的数据
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@SuppressWarnings("deprecation")
public class ContentNavigationLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	 public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	            throws IOException, ServletException {
		if (HeaderAcceptUtils.isAcceptJson(request)) {
			//response.send
			response.setStatus(401);//Unauthorized 
			Writer writer=response.getWriter();
			writer.write("{success:false,msg:'用户没有登录，请先登录!'}");
			writer.close();
			return;
		}
		super.commence(request, response, authException);
	 }

}
