package com.mawujun.user.login;

import org.springframework.security.access.vote.AuthenticatedVoter;

/**
 * 认证主体类型可以访问的内容配置
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class AutheTypeSecurityConfig {

	private String url;
	//AuthenticatedVoter中有这三个常量
	private String autheType;//IS_AUTHENTICATED_FULLY,IS_AUTHENTICATED_REMEMBERED,IS_AUTHENTICATED_ANONYMOUSLY
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAutheType() {
		return autheType;
	}
	public void setAutheType(String autheType) {
		if(AuthenticatedVoter.IS_AUTHENTICATED_FULLY.equals(autheType) || AuthenticatedVoter.IS_AUTHENTICATED_REMEMBERED.equals(autheType)
				|| AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY.equals(autheType)){
			this.autheType = autheType;
		} else{
			throw new IllegalArgumentException("参数值不对，英爱是IS_AUTHENTICATED_FULLY,IS_AUTHENTICATED_REMEMBERED,IS_AUTHENTICATED_ANONYMOUSLY中的一个");
		}
		
	}

}
