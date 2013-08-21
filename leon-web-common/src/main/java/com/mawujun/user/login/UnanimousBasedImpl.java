package com.mawujun.user.login;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.core.Authentication;

public class UnanimousBasedImpl extends UnanimousBased {

	 public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes)
             throws AccessDeniedException {
		//管理员可以访问所有的内容
		  if(((UserDetailsImpl)authentication.getPrincipal()).isAdmin()){
			  return;
		  }
		  super.decide(authentication, object, attributes);
	 }

}
