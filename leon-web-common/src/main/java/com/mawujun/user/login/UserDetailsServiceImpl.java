package com.mawujun.user.login;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.User;
import com.mawujun.user.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user=userService.queryUnique(Cnd.select().andEquals("loginName", username));
		if(user==null || user.isDeleted()){
			throw new UsernameNotFoundException("用户不存在");
		}
		UserDetailsImpl aa=new UserDetailsImpl();
		aa.setUser(user);
		List<String> roleIds=userService.queryRoleId(user.getId());
		roleIds.add("Authentication");
		aa.setRoles(roleIds);
		return aa;
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
