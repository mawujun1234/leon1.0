package com.mawujun.user.login;

/**
 * ip黑名单
 * @author mawujun 16064988@qq.com  
 *
 */
public class IpBlacklist {

	private String url;//可以有通配符/**,/*,/?等
	private String ip;//192.168.1.1/24或192.168.1.1
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
