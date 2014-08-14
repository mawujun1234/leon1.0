package com.mawujun.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 添加了当ajax发起请求的时候，发现登陆超期的时候，就进行叶面跳转，跳转到login.jsp
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class FormAjaxAuthenticationFilter extends FormAuthenticationFilter {

	
	/**
	 * 如果是ajax请求就不保存这个，因为ajax请求后，会返回json，而不是跳转到页面
	 */
    protected void saveRequest(ServletRequest request) {
//    	String accept=((HttpServletRequest)request).getHeader("Accept");
//    	if(accept!=null && accept.indexOf("application/json")==-1){
//    		WebUtils.saveRequest(request);
//    	} 
    	//只保存请求jsp的路径
    	String uri=((HttpServletRequest)request).getRequestURI();
    	if(uri.lastIndexOf(".jsp")!=-1){
    		WebUtils.saveRequest(request);
    	}
    }
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	String accept=((HttpServletRequest)request).getHeader("Accept");
    	if(accept!=null && accept.indexOf("application/json")!=-1){
    		response.getWriter().write("{success:false,root:'"+this.getLoginUrl()+"'}");
    		response.getWriter().close();
    	} else {
    		String loginUrl = getLoginUrl();
            WebUtils.issueRedirect(request, response, loginUrl);
    	}
        
    }
}
