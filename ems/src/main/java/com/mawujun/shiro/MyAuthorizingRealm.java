package com.mawujun.shiro;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.mawujun.user.User;
import com.mawujun.user.UserService;

public class MyAuthorizingRealm extends AuthorizingRealm {
	
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(username));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
	      // 通过表单接收的用户名
	      String username = token.getUsername(); 
	      
	      if( username != null && !"".equals(username) ){ 
	         User user = userService.getByUsername( username ); 
	         
	         if( user != null ){ 
	        	//MyAuthenticationInfo aa=new MyAuthenticationInfo(user,user.getPassword(),getName());
	        	//aa.setLoginTime(new Date());
	        	//return aa;
	        	// user.setLoginDate(new Date());
	            return new SimpleAuthenticationInfo(user,user.getPassword(),getName() ); 
	         } 
	      } 
	      
	      return null; 
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
