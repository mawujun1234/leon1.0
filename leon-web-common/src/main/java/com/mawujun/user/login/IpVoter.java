package com.mawujun.user.login;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

/**
 * ip不能访问的话，其余的操作都免谈了，如果放到投票器的话，就会影响性能，多走几步了，没必要
 * @author mawujun 16064988@qq.com  
 *
 */
@Deprecated
public class IpVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		if(attribute instanceof IpSecurityConfig){
			return true;
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if(IpSecurityConfig.class.isAssignableFrom(clazz)){
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		int result = ACCESS_GRANTED;//ACCESS_ABSTAIN
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
       		 	boolean bool=((IpSecurityConfig)attribute).getIpAddressMatcher().matches(request);
       		 	if(bool){
       		 		result = ACCESS_DENIED;
       		 		break;
       		 	}
            }
        }
		return result;
	}

}
