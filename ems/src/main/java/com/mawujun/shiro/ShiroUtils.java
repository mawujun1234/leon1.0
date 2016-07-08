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
	public static String getUserName(){
		return ShiroUtils.getAuthenticationInfo().getName();
	}
	/**
	 * 获取用户的id
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public static String getUserId(){
		return ShiroUtils.getAuthenticationInfo().getId();
	}
	
	public static User getAuthenticationInfo(){
		return (User)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}
	
	/**
	 * 判断是否已经登录的
	 * @author mawujun qq:16064988 mawujun1234@163.com
	 * @return
	 */
	public static Boolean isLogon(){
		if(SecurityUtils.getSubject().getPrincipals()==null){
			return false;
		}
		return true;
	}

}
