package com.mawujun.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;

public class FilterInvocationSecurityMetadataSourceImpl implements
		FilterInvocationSecurityMetadataSource {
	 private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();   
	 
	 public FilterInvocationSecurityMetadataSourceImpl(){
		 ConfigAttribute configAttribute =    new SecurityConfig("ROLE_aaa");  
			
		 List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
			list.add(configAttribute);
			resourceMap.put("/index.jsp", list);  
	 }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		
		allAttributes.add(resourceMap.get("/index.jsp").iterator().next());  
		
         return allAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

//        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
//            allAttributes.addAll(entry.getValue());
//        }
		
		allAttributes.add(resourceMap.get("/index.jsp").iterator().next());  
		
        return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
