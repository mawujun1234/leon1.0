package com.mawujun.user.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;

public class AjaxRedirectStrategy extends DefaultRedirectStrategy{

	 public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		 String accept=request.getHeader("Accept");
		 if("application/json".equalsIgnoreCase(accept)){
			 
		 } else {
			 super.sendRedirect(request, response, url);
		 }
	 }

}
