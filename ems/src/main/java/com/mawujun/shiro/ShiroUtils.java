package com.mawujun.shiro;

import org.apache.shiro.SecurityUtils;

import com.mawujun.user.User;

public class ShiroUtils {
	public static String getLoginName(){
		return SecurityUtils.getSubject().getPrincipal().toString();
	}
	/**
	 * 获取用户的姓名
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static String getName(){
		return ShiroUtils.getAuthenticationInfo().getName();
	}
	
	public static User getAuthenticationInfo(){
		return (User)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

}
