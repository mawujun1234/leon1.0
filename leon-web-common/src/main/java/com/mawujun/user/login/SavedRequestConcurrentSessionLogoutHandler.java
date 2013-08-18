package com.mawujun.user.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.Assert;

/**
 * 当强制用户失效和后来用户把前面用户踢出去失效后的。保存用户要访问的内容，当用户重新登录就可以返回到他的页面，
 * 注意要和UsernamePasswordAuthenticationFilter整合了一起用。
 * 还有ExceptionTranslationFilter种会话超期后重新登录进行区别和整合
 * @author mawujun 16064988@qq.com  
 *
 */
public class SavedRequestConcurrentSessionLogoutHandler implements LogoutHandler {

	private RequestCache requestCache;

	public SavedRequestConcurrentSessionLogoutHandler(RequestCache requestCache) {
		Assert.notNull(requestCache, "requestCache cannot be null");
		this.requestCache = requestCache;
	}
	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		requestCache.saveRequest(request, response);
	}

}
