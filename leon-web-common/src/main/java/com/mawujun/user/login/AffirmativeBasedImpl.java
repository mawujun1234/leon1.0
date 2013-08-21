package com.mawujun.user.login;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AffirmativeBasedImpl extends AffirmativeBased {
	//public boolean defaultAllowAccess=false;//默认是都允许访问

	  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
	            throws AccessDeniedException {
		//管理员可以访问所有的内容
		  if(((UserDetailsImpl)authentication.getPrincipal()).isAdmin()){
			  return;
		  }
		  super.decide(authentication, object, configAttributes);
	  }


}
