package com.mawujun.user.login;

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

import com.mawujun.role.RoleService;
import com.mawujun.utils.StringUtils;

public class FilterInvocationSecurityMetadataSourceImpl implements
		FilterInvocationSecurityMetadataSource {
	
	private RoleService roleService;
	
	 private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
	 
	 
	 public FilterInvocationSecurityMetadataSourceImpl(){
		 
//		 
//		 ConfigAttribute configAttribute =    new SecurityConfig("ROLE_aaa");  
//			
//		 List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
//			list.add(configAttribute);
//			resourceMap.put("/index.jsp", list);  
	 }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		 final HttpServletRequest request = ((FilterInvocation) object).getRequest();
	        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
	        	//RequestMatcher这里把本来是调用这个的matches方法
//	            if (entry.getKey().matches(request)) {
//	                return entry.getValue();
//	            }
	        	if(entry.getKey().equals(getRequestPath(request))){
	        		return entry.getValue();
	        	}
	        }
	        return null;
	        
	}

	private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();

        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        url = url.toLowerCase();

        return url;
    }
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		initResourceMap();
		
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
	}
	
	public void initResourceMap(){
		List<Map<String,Object>> funRoles=roleService.queryList("queryFun");
		 for(Map<String,Object> map:funRoles){
			 if(!StringUtils.hasLength(map.get("URL").toString())){
				 continue;
			 }
			ConfigAttribute configAttribute =    new SecurityConfig(map.get("ROLE_ID").toString());  
				
			List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
			list.add(configAttribute);
			resourceMap.put(map.get("URL").toString(), list);  
		 }
	}
	

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return FilterInvocation.class.isAssignableFrom(clazz);
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
