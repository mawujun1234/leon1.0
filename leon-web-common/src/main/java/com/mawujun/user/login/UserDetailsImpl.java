package com.mawujun.user.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mawujun.role.Role;
import com.mawujun.user.User;

public class UserDetailsImpl implements UserDetails {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;

	}
	
	public void setRoles(List<String> roleIds) {
		if(roleIds!=null){
		
			for(String id:roleIds){
				SimpleGrantedAuthority sga=new SimpleGrantedAuthority(id);
				authorities.add(sga);
			}

		} 
	}
	public boolean isAdmin() {
		return user.isAdmin();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnable();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
