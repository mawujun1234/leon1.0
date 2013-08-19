package com.mawujun.user.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class ContentNavigationAccessDeniedHandlerImpl extends
		AccessDeniedHandlerImpl {
	 public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
	            throws IOException, ServletException {
		 if (HeaderAcceptUtils.isAcceptJson(request)) {
				//response.send
				response.setStatus(403);//Unauthorized 
				Writer writer=response.getWriter();
				writer.write("{success:false,msg:'没有权限访问该资源!'}");
				writer.close();
				return;
			}
			super.handle(request, response, accessDeniedException);
	 }
}
