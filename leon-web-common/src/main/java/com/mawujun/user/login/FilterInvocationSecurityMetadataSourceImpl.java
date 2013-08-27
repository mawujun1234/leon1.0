package com.mawujun.user.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.mawujun.fun.Fun;
import com.mawujun.role.Role;
import com.mawujun.role.RoleFunObserver;
import com.mawujun.role.RoleService;
import com.mawujun.utils.StringUtils;

public class FilterInvocationSecurityMetadataSourceImpl implements
		FilterInvocationSecurityMetadataSource,RoleFunObserver {
	
	private RoleService roleService;
	 private String rolePrefix = "ROLE_";
	 
//	private List<String> authenticatedFullyUrls;//完整登陆可以访问的url范围
//	private List<String> authenticatedRememberedUrls;//用记住账号密码登陆的时候可以访问的url范围
//	private List<String> authenticatedAnonymouslyUrls;//匿名可以访问的url范围
	
	 private List<AutheTypeSecurityConfig> authenticatedUrls;//完整登陆可以访问的url范围
	
	 private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();  
	 
	 
	 public FilterInvocationSecurityMetadataSourceImpl(){

	 }
	 /**
	  * 更新的权限信息，所以是url要完全匹配才可以
	  * @author mawujun 16064988@qq.com 
	  * @param object HttpServletRequest
	  * @return
	  * @throws IllegalArgumentException
	  */
	 protected Collection<ConfigAttribute> getAttributesFully(String url)
				throws IllegalArgumentException {
		 //Set<ConfigAttribute> result=new LinkedHashSet<ConfigAttribute>();
		 //final HttpServletRequest request = ((FilterInvocation) object).getRequest();
	        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
	        	//RequestMatcher这里把本来是调用这个的matches方法
	            if (((AntPathRequestMatcher)entry.getKey()).getPattern().equalsIgnoreCase(url)) {
	                return entry.getValue();
	            }

	        }
	        return null;
	 }
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		Set<ConfigAttribute> result=new LinkedHashSet<ConfigAttribute>();
		 final HttpServletRequest request = ((FilterInvocation) object).getRequest();
	        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
	        	//RequestMatcher这里把本来是调用这个的matches方法
	            if (entry.getKey().matches(request)) {
//	                //return entry.getValue();
	            	result.addAll(entry.getValue());
	            	
	            }

	        }
	        return (result==null||result.size()==0)?null:result;
	        
	}

	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		initResourceMap();
		
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
//这里是不是永远只返回一种了，要么就是Role，要么就是Authentication，要么就是Ip
//		这里别忘记测试了，不过先完成ip的测试加上去，或者就不使用Ip头投票器了，直接在这里判断，如果 有ip禁止就直接抛出异常，更简单
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
	}
	
	public void initResourceMap(){
		List<Map<String,Object>> funRoles=roleService.queryRoleUrl();
		 for(Map<String,Object> map:funRoles){
			 if(!StringUtils.hasLength(map.get("URL").toString())){
				 continue;
			 }
			 AntPathRequestMatcher matcher=new AntPathRequestMatcher(map.get("URL").toString());
			 ConfigAttribute configAttribute =    new SecurityConfig(rolePrefix+map.get("ROLE_ID"));  
			 if(resourceMap.containsKey(matcher)){
				 resourceMap.get(matcher).add(configAttribute);
			 } else{
				LinkedHashSet<ConfigAttribute> list=new LinkedHashSet<ConfigAttribute>();
				list.add(configAttribute);
				resourceMap.put(matcher, list);  
			 }
			
		 }
		 

		 if(authenticatedUrls!=null && authenticatedUrls.size()>0){
			 for(AutheTypeSecurityConfig autheTypeSecurityConfig:authenticatedUrls){
				AntPathRequestMatcher matcher=new AntPathRequestMatcher(autheTypeSecurityConfig.getUrl());
				ConfigAttribute configAttribute =    new SecurityConfig(autheTypeSecurityConfig.getAutheType());
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


	public String getRolePrefix() {
		return rolePrefix;
	}
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}
	
	public void setAuthenticatedUrls(List<AutheTypeSecurityConfig> authenticatedUrls) {
		this.authenticatedUrls = authenticatedUrls;
	}
	
	 /**
	  * 移除某个url上的角色
	  * @author mawujun 16064988@qq.com 
	  * @param object HttpServletRequest
	  * @param role_id
	  */
	 public void removeConfigAttribute(String url,String role_id){
		 Collection<ConfigAttribute> roleIds=getAttributesFully(url);
		 if(roleIds==null){
			 return;
		 }
		 ConfigAttribute configAttribute =    new SecurityConfig(rolePrefix+role_id);
		 roleIds.remove(configAttribute);
	 }
	 /**
	  * 网某个url上添加角色
	  * @author mawujun 16064988@qq.com 
	  * @param url
	  * @param role_id
	  */
	 public void addConfigAttribute(String url,String role_id){
		 Collection<ConfigAttribute> roleIds=getAttributesFully(url);
		 if(roleIds==null){
			 return;
		 }
		 ConfigAttribute configAttribute =    new SecurityConfig(rolePrefix+role_id);
		 roleIds.add(configAttribute);
	 }
	@Override
	public void create(Role role, Fun fun) {
		// TODO Auto-generated method stub
		addConfigAttribute(fun.getUrl(),role.getId());
	}
	@Override
	public void destroy(Role role, Fun fun) {
		// TODO Auto-generated method stub
		removeConfigAttribute(fun.getUrl(),role.getId());
	}


}
