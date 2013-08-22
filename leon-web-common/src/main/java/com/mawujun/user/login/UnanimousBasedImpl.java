package com.mawujun.user.login;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
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
		  } else {
			  int grant = 0;
		      int abstain = 0;
		        
			  for(AccessDecisionVoter voter : getDecisionVoters()) {
	                int result = voter.vote(authentication, object, attributes);

	                if (logger.isDebugEnabled()) {
	                    logger.debug("Voter: " + voter + ", returned: " + result);
	                }

	                switch (result) {
	                case AccessDecisionVoter.ACCESS_GRANTED:
	                    grant++;

	                    break;

	                case AccessDecisionVoter.ACCESS_DENIED:
	                    throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied",
	                            "Access is denied"));

	                default:
	                    abstain++;

	                    break;
	                }
	            }
			  
			  // To get this far, there were no deny votes
		        if (grant > 0) {
		            return;
		        }
		  }
		  //super.decide(authentication, object, attributes);
	 }

}
