package com.mawujun.shiro;

import org.apache.shiro.SecurityUtils;

import com.mawujun.user.User;

public class ShiroUtils {
	public static String getLoginName(){
		return SecurityUtils.getSubject().getPrincipal().toString();
	}
	
	public static User getAuthenticationInfo(){
		return (User)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

}
