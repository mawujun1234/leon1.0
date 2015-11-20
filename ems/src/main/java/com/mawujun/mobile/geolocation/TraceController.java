package com.mawujun.mobile.geolocation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;

@Controller
public class TraceController {
	
	@Autowired
	private GeolocationController geolocationController;
	@Autowired
	private GeolocationService geolocationService;
	/**
	 * 查询某天上过线的作业单位
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	@RequestMapping("/trace/queryHistoryWorkunit.do")
	public List<GeolocationVO> queryHistoryWorkunit(String loc_time){
		
		return geolocationService.queryHistoryWorkunit(loc_time);
	}
	
	/**
	 * 查询某天中的某个作业单位的
	 * @author mawujun 16064988@qq.com 
	 * @param locationDate
	 * @return
	 */
	@RequestMapping("/trace/queryHistoryTrace.do")
	public List<Trace> queryHistoryTrace(String loc_time,String loginName){
	
		JsonConfigHolder.setDatePattern("HH:mm:ss");
		return geolocationService.queryHistoryTrace(loc_time, loginName);
		
	} 
	
	@RequestMapping("/trace/queryHistoryTraceList.do")
	public List<TraceList> queryHistoryTraceList(String sessionId){

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return geolocationService.queryHistoryTraceList(sessionId);
		
	} 

}
