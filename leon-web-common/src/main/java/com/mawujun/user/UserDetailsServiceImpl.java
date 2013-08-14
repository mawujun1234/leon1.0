package com.mawujun.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mawujun.exception.BussinessException;
import com.mawujun.repository.cnd.Cnd;

public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user=userService.queryUnique(Cnd.select().andEquals("loginName", username));
		if(user==null){
			throw new BussinessException("用户不存在");
		}
		UserDetailsImpl aa=new UserDetailsImpl();
		aa.setPassword(user.getPassword());
		aa.setUserName(aa.getUsername());
		// TODO Auto-generated method stub
		return aa;
	}
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
