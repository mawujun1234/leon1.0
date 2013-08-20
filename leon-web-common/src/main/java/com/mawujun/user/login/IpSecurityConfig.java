package com.mawujun.user.login;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.IpAddressMatcher;
import org.springframework.util.Assert;

public class IpSecurityConfig implements ConfigAttribute {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IpAddressMatcher ipAddressMatcher;
	private String ip;

	public IpSecurityConfig(String ipAddress) {
		Assert.hasText(ipAddress, "You must provide a configuration attribute");
		this.ip=ipAddress;
		this.ipAddressMatcher = new IpAddressMatcher(ipAddress);
	}

	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		return ip;
	}
	
	public int hashCode() {
        return this.ip.hashCode();
    }
	
	 public boolean equals(Object obj) {
	        if (obj instanceof ConfigAttribute) {
	            ConfigAttribute attr = (ConfigAttribute) obj;

	            return this.ip.equals(attr.getAttribute());
	        }

	        return false;
	    }

	public IpAddressMatcher getIpAddressMatcher() {
		return ipAddressMatcher;
	}

}
