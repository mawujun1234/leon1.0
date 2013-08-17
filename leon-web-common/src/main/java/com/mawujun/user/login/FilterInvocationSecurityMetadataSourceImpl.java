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
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.mawujun.role.RoleService;
import com.mawujun.utils.StringUtils;

public class FilterInvocationSecurityMetadataSourceImpl implements
		FilterInvocationSecurityMetadataSource {
	
	private RoleService roleService;
	
	private List<String> needAuthenticationUrls;
	
	 private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();  
	 
	 
	 public FilterInvocationSecurityMetadataSourceImpl(){

	 }
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		 final HttpServletRequest request = ((FilterInvocation) object).getRequest();
	        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
	        	//RequestMatcher这里把本来是调用这个的matches方法
	            if (entry.getKey().matches(request)) {
	                return entry.getValue();
	            }
//	        	if(entry.getKey().equals(getRequestPath(request))){
//	        		return entry.getValue();
//	        	}
	        }
	        return null;
	        
	}

	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		initResourceMap();
		
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
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
			 AntPathRequestMatcher matcher=new AntPathRequestMatcher(map.get("URL").toString());
			 ConfigAttribute configAttribute =    new SecurityConfig(map.get("ROLE_ID").toString());  
			 if(resourceMap.containsKey(matcher)){
				 resourceMap.get(matcher).add(configAttribute);
			 } else{
				List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
				list.add(configAttribute);
				resourceMap.put(matcher, list);  
			 }
			
		 }
		 
		 //添加必须认证才能访问的url
		 if(needAuthenticationUrls!=null && needAuthenticationUrls.size()>0){
			 for(String url:needAuthenticationUrls){
				//添加所有的路径，都必须是认证过的才能访问
				//Authentication只是作为一个标识符，可以让beforeInvocation中不范虎null	
				ConfigAttribute configAttribute =    new SecurityConfig("Authentication"); 
				List<ConfigAttribute> list=new ArrayList<ConfigAttribute>();
				list.add(configAttribute);
				resourceMap.put(new AntPathRequestMatcher(url), list);  
			 }
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
	public List<String> getNeedAuthenticationUrls() {
		return needAuthenticationUrls;
	}
	public void setNeedAuthenticationUrls(List<String> needAuthenticationUrls) {
		this.needAuthenticationUrls = needAuthenticationUrls;
	}

}
