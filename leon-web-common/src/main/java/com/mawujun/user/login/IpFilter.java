package com.mawujun.user.login;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

public class IpFilter extends GenericFilterBean {
	public final static String ACCESS_DENY_RASION="ACCESS_DENY_RASION";

	//private List<IpBlacklist> ipBlacklists;
	 private  Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();  
	 private RedirectStrategy redirectStrategy=new ContentNavigationDefaultRedirectStrategy();
	 private String denyUrl;
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//.matches(request)
		// TODO Auto-generated method stub
		String address=request.getRemoteAddr();
		if("127.0.0.1".equalsIgnoreCase(address)){
			try {
				address=InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean bool=false;
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
        	//RequestMatcher这里把本来是调用这个的matches方法
            if (entry.getKey().matches((HttpServletRequest)request)) {
        		for (ConfigAttribute attribute : entry.getValue()) {
               		 	bool=((IpSecurityConfig)attribute).getIpAddressMatcher().matches(address);
               		 	if(bool){
               		 		//throw 
               		 		request.setAttribute(ACCESS_DENY_RASION, "您的ip地址不允许访问本系统");
               		 		redirectStrategy.sendRedirect((HttpServletRequest)request, (HttpServletResponse)response, denyUrl);
               		 		return;
               		 	}
                }
            	
            }
        }
		chain.doFilter(request, response);

	}
	public void setIpBlacklists(List<IpBlacklist> ipBlacklists) {
		//this.ipBlacklists = ipBlacklistParam;
		
		//添加黑名单，符合这些ip的机器访问不了
		 if(ipBlacklists!=null && ipBlacklists.size()>0){
			 for(IpBlacklist ipBlacklist:ipBlacklists){
				AntPathRequestMatcher matcher=new AntPathRequestMatcher(ipBlacklist.getUrl());
				ConfigAttribute configAttribute =    new IpSecurityConfig(ipBlacklist.getIp());
				if(resourceMap.containsKey(matcher)){
					 resourceMap.get(matcher).add(configAttribute);
				 } else{
					 List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
					 list.add(configAttribute);
					 resourceMap.put(matcher, list); 
				 }
			 }
		 }
	}
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	public void setDenyUrl(String denyUrl) {
		this.denyUrl = denyUrl;
	}

	
}
