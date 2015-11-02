package com.mawujun.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.mobile.geolocation.GeolocationController;

public class ShiroSessionListener extends SessionListenerAdapter {

	private GeolocationController geolocationController;

	public void onExpiration(Session session) {// 会话过期时触发
		改成直接结束应用，然后看看会不会进入到这来
		http://jinnianshilongnian.iteye.com/blog/2028675
			http://jinnianshilongnian.iteye.com/blog/2029717/
		removeWaringGpsMap();
	}



	public void removeWaringGpsMap() {
		if (geolocationController == null) {
			geolocationController = SpringContextHolder.getBean(GeolocationController.class);
		}
		System.out.println("22222222222222222");
		//String loginName=ShiroUtils.getAuthenticationInfo().getUsername();
		// no-op
		//geolocationController.getWaringGpsMap().remove(loginName);
	}

	public GeolocationController getGeolocationController() {
		return geolocationController;
	}

	public void setGeolocationController(GeolocationController geolocationController) {
		this.geolocationController = geolocationController;
	}

}
