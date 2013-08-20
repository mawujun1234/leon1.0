package com.mawujun.user.login;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class IpVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		// TODO Auto-generated method stub
		 final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		 request.getRemoteAddr();
		//也就是说哪些url地址可以被哪些ip地址访问或拒绝。可能还会有时效字段。禁止多长时间。
		return 0;
	}

}
