package com.mawujun.panera.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportController {

	@Resource
	private CustomerRepository customerRepository;
	
	@RequestMapping("/report/getData")
	public List<Map<String,Object>> getData(String dimession,String meausre){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if("continent".equalsIgnoreCase(dimession)){
			for(int i=0;i<5;i++){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "continent"+i);
				map.put("data", i+Math.random()*10);
				list.add(map);
			}
		} else if("country".equalsIgnoreCase(dimession)){
			for(int i=0;i<10;i++){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", "country"+i);
				map.put("data", i+Math.random()*10);
				list.add(map);
			}	
			
		} else if("businessPhase".equalsIgnoreCase(dimession)){
			
		} else if("customerSource".equalsIgnoreCase(dimession)){
			
		} else if("customerProperty".equalsIgnoreCase(dimession)){
			
		} else if("star".equalsIgnoreCase(dimession)){
			
		}
		return list;
		
	} 
}
