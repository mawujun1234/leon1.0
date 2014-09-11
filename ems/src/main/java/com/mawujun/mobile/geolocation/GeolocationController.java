package com.mawujun.mobile.geolocation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeolocationController {
	
	@RequestMapping("/geolocation/mobile/upload.do")
	public String upload(String longitude,String latitude,String uuid,String loginName){
		//地理信息没有上报
		System.out.println(longitude);
		System.out.println(latitude);
		System.out.println(uuid);
		System.out.println(loginName);
		return "success";
	}

}
