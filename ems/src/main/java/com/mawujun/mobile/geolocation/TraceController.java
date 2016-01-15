package com.mawujun.mobile.geolocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.mobile.login.WaringGps;

@Controller
public class TraceController {
	
	@Autowired
	private GeolocationController geolocationController;
	@Autowired
	private GeolocationService geolocationService;
	
	/**
	 * 查询正在工作，也表示正在上传gps数据的作业单位
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/trace/queryWorkingWorkunit.do")
	@ResponseBody
	public List<WaringGps> queryWorkingWorkunit(){
		List<WaringGps> list=new ArrayList<WaringGps>();
		for(Entry<String,WaringGps> entry:geolocationController.getWaringGpsMap().entrySet()){
			WaringGps waringGps=entry.getValue();
			if(waringGps.getIsUploadGps()){
				//waringGps.setTraceListes(queryHistoryTraceList(waringGps.getSessionId()));
				list.add(waringGps);
	
			}
			
		}
		
//		WaringGps waringGps=new WaringGps();
//		waringGps.setLasted_longitude("121.518223");
//		waringGps.setLasted_latitude("29.826982");
//		waringGps.setName("哈哈");
//		waringGps.setLoginName("yinzhou1");
//		waringGps.setSessionId("913b3633-a350-48ff-80b8-3d33ee96f59d");
//		waringGps.setTraceListes(queryHistoryTraceList(waringGps.getSessionId()));
//		list.add(waringGps);
		
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return list;
		
	}
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
