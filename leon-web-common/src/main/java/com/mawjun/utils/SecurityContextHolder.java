package com.mawjun.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.mawujun.user.login.UserDetailsImpl;

/**
 * 简化了一些获取方法
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class SecurityContextHolder {

	public static void clearContext() {
		SecurityContextHolder.clearContext();
    }

    /**
     * Obtain the current <code>SecurityContext</code>.
     *
     * @return the security context (never <code>null</code>)
     */
    public static SecurityContext getContext() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext();
    }

    /**
     * Primarily for troubleshooting purposes, this method shows how many times the class has re-initialized its
     * <code>SecurityContextHolderStrategy</code>.
     *
     * @return the count (should be one unless you've called {@link #setStrategyName(String)} to switch to an alternate
     *         strategy.
     */
    public static int getInitializeCount() {
        return org.springframework.security.core.context.SecurityContextHolder.getInitializeCount();
    }
    
    public static Authentication getAuthentication() {
    	return org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
    }
    
    public static UserDetailsImpl getUserDetailsImpl(){
    	Authentication currentAuth = getAuthentication();
		UserDetailsImpl userDetail=(UserDetailsImpl)currentAuth.getPrincipal();
		return userDetail;
    }

}
