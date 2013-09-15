package com.mawujun.user.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
	
	public void setRoles(Set<String> roleIds) {
		if(roleIds!=null){
		
			for(String id:roleIds){
				SimpleGrantedAuthority sga=new SimpleGrantedAuthority(id);
				//if(!authorities.contains(sga)){		
					authorities.add(sga);
				//}
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
	
	public String getId() {
		// TODO Auto-generated method stub
		return user.getId();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		if(isAdmin()){
			return true;
		}
		//System.out.println("======================="+user.isAccountExpired());
		// TODO Auto-generated method stub
		return !user.isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		if(isAdmin()){
			return true;
		}
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
		if(isAdmin()){
			return true;
		}
		// TODO Auto-generated method stub
		return user.isEnable();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getUsername() == null) ? 0 : this.getUsername().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		if (this.getUsername() == null) {
			if (other.getUsername() != null)
				return false;
		} else if (!this.getUsername().equals(other.getUsername()))
			return false;
		return true;
	}
}
