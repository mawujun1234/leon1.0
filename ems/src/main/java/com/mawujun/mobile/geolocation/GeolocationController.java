package com.mawujun.mobile.geolocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.mobile.login.MobileLoginController;
import com.mawujun.mobile.login.WaringGps;

@Controller
public class GeolocationController {
	static Logger logger=LogManager.getLogger(GeolocationController.class);
	@Autowired
	private GeolocationService geolocationService;
	
	private Map<String,WaringGps> waringGpsMap=new HashMap<String,WaringGps>();
	
	@RequestMapping("/geolocation/mobile/upload.do")
	public String upload(String longitude,String latitude,String uuid,String loginName) {
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
		updateGpsUploadTime(SecurityUtils.getSubject().getSession().getId().toString(),longitude,latitude);
		return "success";
	}
	
	/**
	 * 更新某个作业单位最近一次gps上传信息
	 * @author mawujun 16064988@qq.com 
	 * @param loginName
	 */
	public void updateGpsUploadTime(String loginName,String lasted_longitude, String lasted_latitude) {
		WaringGps waringGps=waringGpsMap.get(loginName);
		waringGps.setIsUploadGps(true);
		waringGps.setLastedUploadTime(new Date());
		waringGps.setLasted_longitude(lasted_longitude);
		waringGps.setLasted_latitude(lasted_latitude);
		
	}
	
	
	/**
	 * 当登录了，单没有gps信息上传过来的作业单位
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/geolocation/unuploadGpsWorkunit.do")
	@ResponseBody
	public List<WaringGps> unuploadGpsWorkunit(String status) {
		List<WaringGps> list=new ArrayList<WaringGps>();
		if("unuploadgps".equals(status)){
			for(Entry<String,WaringGps> entry:waringGpsMap.entrySet()){
				WaringGps waringGps=entry.getValue();
				if(!waringGps.getIsUploadGps()){
					list.add(waringGps);
				}
				
			}
		} else if("logined".equals(status)) {
			for(Entry<String,WaringGps> entry:waringGpsMap.entrySet()){
				WaringGps waringGps=entry.getValue();
				list.add(waringGps);
				
			}
		} else {
			for(Entry<String,WaringGps> entry:waringGpsMap.entrySet()){
				WaringGps waringGps=entry.getValue();
				list.add(waringGps);
				
			}
		}
		
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return list;
	}
	/**
	 * 查询正在工作，也表示正在上传gps数据的作业单位
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/geolocation/queryWorkingWorkunit.do")
	@ResponseBody
	public List<WaringGps> queryWorkingWorkunit(){
		List<WaringGps> list=new ArrayList<WaringGps>();
		for(Entry<String,WaringGps> entry:waringGpsMap.entrySet()){
			WaringGps waringGps=entry.getValue();
			if(waringGps.getIsUploadGps()){
				list.add(waringGps);
			}
			
		}
		return list;
		
	}

	public Map<String, WaringGps> getWaringGpsMap() {
		return waringGpsMap;
	}
	

}
