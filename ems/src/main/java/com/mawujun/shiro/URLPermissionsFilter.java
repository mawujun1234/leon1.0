package com.mawujun.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;



public class URLPermissionsFilter extends PermissionsAuthorizationFilter {
	/** 
     *@param mappedValue 指的是在声明url时指定的权限字符串，如/User/create.do=perms[User:create].我们要动态产生这个权限字符串，所以这个配置对我们没用 
     */  
    public boolean isAccessAllowed(ServletRequest request,  
            ServletResponse response, Object mappedValue) throws IOException {  
    	Subject subject = SecurityUtils.getSubject(); 
    	if("admin".equalsIgnoreCase(subject.getPrincipal().toString())){
    		return true;
    	}
         return super.isAccessAllowed(request, response, buildPermissions(request));  
    }  
    /** 
     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm 
     * @param request 
     * @return 
     */  
    protected String[] buildPermissions(ServletRequest request) {  
        String[] perms = new String[1];  
        HttpServletRequest req = (HttpServletRequest) request;  
        String path = req.getServletPath();  
        perms[0] = path;//path直接作为权限字符串  
        /*String regex = "/(.*?)/(.*?)\\.(.*)"; 
        if(url.matches(regex)){ 
            Pattern pattern = Pattern.compile(regex); 
            Matcher matcher = pattern.matcher(url); 
            String controller =  matcher.group(1); 
            String action = matcher.group(2); 
             
        }*/  
        return perms;  
    }  
    
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
//            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
//            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
//            String unauthorizedUrl = getUnauthorizedUrl();
//            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
//            if (StringUtils.hasText(unauthorizedUrl)) {
//                WebUtils.issueRedirect(request, response, unauthorizedUrl);
//            } else {
//                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            }
        	 String unauthorizedUrl = getUnauthorizedUrl();
        	String accept=((HttpServletRequest)request).getHeader("Accept");

        	if(accept!=null && accept.indexOf("application/json")!=-1){
        		response.getWriter().write("{\"success\":false,\"reasons\":{\"code\":\"noPermission\"},\"root\":\""+unauthorizedUrl+"\"}");
        		response.getWriter().close();
        	} else {
             
              //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
              if (StringUtils.hasText(unauthorizedUrl)) {
                  WebUtils.issueRedirect(request, response, unauthorizedUrl);
              } else {
                  WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
              }
        	}
        }
        return false;
    }
}
