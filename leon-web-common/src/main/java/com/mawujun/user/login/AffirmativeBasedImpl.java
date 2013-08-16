package com.mawujun.user.login;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AffirmativeBasedImpl extends AbstractAccessDecisionManager {
	//public boolean defaultAllowAccess=false;//默认是都允许访问

	  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
	            throws AccessDeniedException {
		  //管理员可以访问所有的内容
		  if(((UserDetailsImpl)authentication.getPrincipal()).isAdmin()){
			  return;
		  }
		  

		  Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);
		  for (ConfigAttribute attribute : configAttributes) {
	            if (this.supports(attribute)) {

	                // Attempt to find a matching granted authority
	                for (GrantedAuthority authority : authorities) {
	                    if (attribute.getAttribute().equals(authority.getAuthority())) {
	                    	return;
	                    }
	                }
	            }
	        }
		  throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied",
                  "Access is denied"));
	  }
	  Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
	        return authentication.getAuthorities();
	  }

}
