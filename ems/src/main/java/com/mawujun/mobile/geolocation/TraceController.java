package com.mawujun.mobile.geolocation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TraceController {
	
	/**
	 * 查询某天中的某个作业单位的
	 * @author mawujun 16064988@qq.com 
	 * @param locationDate
	 * @return
	 */
	@RequestMapping("/trace/queryHistoryTrace.do")
	public List<String> queryHistoryTrace(String locationDate,String loginName){
		List<String> result=new ArrayList<String>();
		result.add("1111");
		result.add("2222");
		result.add("3333");
		return result;
		
	} 

}
