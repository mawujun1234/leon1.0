package com.mawujun.user.login;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 重新封装了当设置了全局拒绝的时候，爆出不能访问的异常
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class FilterSecurityInterceptorImpl extends FilterSecurityInterceptor {

	  public void invoke(FilterInvocation fi) throws IOException, ServletException {
		  try {
	        super.invoke(fi);
		  } catch(IllegalArgumentException ex){
			  if (SecurityContextHolder.getContext().getAuthentication() == null) {
//		            credentialsNotFound(messages.getMessage("AbstractSecurityInterceptor.authenticationNotFound",
//		                    "An Authentication object was not found in the SecurityContext"), object, attributes);
				  AuthenticationCredentialsNotFoundException exception = new AuthenticationCredentialsNotFoundException("用户还没有登录");
				  logger.debug("用户还没有登录");
				  throw exception;
		      } else{
		    	  logger.debug(ex.getMessage());
				  throw new AccessDeniedException("全局访问拒绝",ex);
		      }
			 
		  }
	    }

}
