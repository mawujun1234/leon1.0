package com.mawujun.user.login;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;

/**
 * 根据头信息来回
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class ContentNavigationDefaultRedirectStrategy extends
		DefaultRedirectStrategy {
	 public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		 if (HeaderAcceptUtils.isAcceptJson(request)) {
				//response.send
			 	//会话失效，或被别人踢出来了
				response.setStatus(401);//Unauthorized 
				Writer writer=response.getWriter();
				writer.write("{success:false,msg:'会话失效，被别人踢出来了或者被强制失效了!'}");
				writer.close();
				return;
			}
			super.sendRedirect(request, response, url);
	 }
}
