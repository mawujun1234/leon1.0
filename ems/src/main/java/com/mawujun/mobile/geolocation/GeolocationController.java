package com.mawujun.mobile.geolocation;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.mobile.login.MobileLoginController;

@Controller
public class GeolocationController {
	static Logger logger=LogManager.getLogger(GeolocationController.class);
	@Autowired
	private GeolocationService geolocationService;
	
	@RequestMapping("/geolocation/mobile/upload.do")
	public String upload(String longitude,String latitude,String uuid,String loginName){
		//地理信息没有上报
		//System.out.println(longitude);
		//System.out.println(latitude);
		//System.out.println(uuid);
		//System.out.println(loginName);
		
		logger.info("==========================================================================");
		logger.info("经度:{},维度:{},uuid:{},loginname:{}",longitude,latitude,uuid,loginName);
		
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
		MobileLoginController.updateGpsUploadTime(loginName);
		return "success";
	}

}
