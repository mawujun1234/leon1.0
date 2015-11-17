package com.mawujun.mobile.geolocation;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import com.mawujun.controller.spring.SpringContextHolder;

public class ShiroSessionListener extends SessionListenerAdapter {

	private GeolocationController geolocationController;

	public void onExpiration(Session session) {// 会话过期时触发
		//改成直接结束应用，然后看看会不会进入到这来
		//http://jinnianshilongnian.iteye.com/blog/2028675
		//	http://jinnianshilongnian.iteye.com/blog/2029717/
		super.onExpiration(session);
		removeWaringGpsMap(session);
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		super.onStop(session);
		 removeWaringGpsMap(session);
	}

	public void removeWaringGpsMap(Session session) {
		if (geolocationController == null) {
			geolocationController = (GeolocationController)SpringContextHolder.getBean("geolocationController");
		}
		//System.out.println(session.getId());
		//String loginName=ShiroUtils.getAuthenticationInfo().getUsername();
		// no-op
		//System.out.println("=============================================================="+session.getId().toString());
		geolocationController.getWaringGpsMap().remove(session.getId().toString());
	}

	public GeolocationController getGeolocationController() {
		return geolocationController;
	}

	public void setGeolocationController(GeolocationController geolocationController) {
		this.geolocationController = geolocationController;
	}



	

}
