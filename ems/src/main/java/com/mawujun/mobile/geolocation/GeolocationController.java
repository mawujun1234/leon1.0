package com.mawujun.mobile.geolocation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeolocationController {
	@Autowired
	private GeolocationService geolocationService;
	
	@RequestMapping("/geolocation/mobile/upload.do")
	public String upload(String longitude,String latitude,String uuid,String loginName){
		//地理信息没有上报
		System.out.println(longitude);
		System.out.println(latitude);
		System.out.println(uuid);
		System.out.println(loginName);
		
		if(uuid==null && loginName==null){
			return "failure";
		}
		
		
		Geolocation geolocation=new Geolocation();
		geolocation.setCreateDate(new Date());
		geolocation.setLatitude(latitude);
		geolocation.setLongitude(longitude);
		geolocation.setLoginName(loginName);
		geolocation.setUuid(uuid);
		geolocation.setCreateDate(new Date());
		
		geolocationService.create(geolocation);
		
		return "success";
	}

}
