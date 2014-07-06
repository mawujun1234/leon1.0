package com.mawujun.panera.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class ReportController {
	String jsonStr="{id:'none',name:'未回复'},{id:'replay',name:'客户回复'},{id:'discuss',name:'讨论价格'},{id:'send',name:'送样'},{id:'deal',name:'成交'}";
	@Resource
	private CustomerRepository customerRepository;
	
	@RequestMapping("/report/getData")
	public List<ReportEntity> getData(String dimession,String meausre){
		List<ReportEntity> list=new ArrayList<ReportEntity>();
		if("continent".equalsIgnoreCase(dimession)){
			list=  customerRepository.queryByContinent();

		} else if("country".equalsIgnoreCase(dimession)){
			list= customerRepository.queryByCountry();
			
		} else if("businessPhase".equalsIgnoreCase(dimession)){
			list=  customerRepository.queryByBusinessPhase();

			for(ReportEntity reportEntity:list){
				if("none".equals(reportEntity.getId())){
					reportEntity.setName("未回复");
				} else if("replay".equals(reportEntity.getId())){
					reportEntity.setName("客户回复");
				}else if("discuss".equals(reportEntity.getId())){
					reportEntity.setName("讨论价格");
				}else if("send".equals(reportEntity.getId())){
					reportEntity.setName("送样");
				}else if("deal".equals(reportEntity.getId())){
					reportEntity.setName("成交");
				}
				
			}
		} else if("customerSource".equalsIgnoreCase(dimession)){
			list=  customerRepository.queryByCustomerSource();
		} else if("customerProperty".equalsIgnoreCase(dimession)){
			list=  customerRepository.queryByCustomerProperty();
		} else if("star".equalsIgnoreCase(dimession)){
			list=  customerRepository.queryByStar();
			for(ReportEntity reportEntity:list){
				reportEntity.setName(reportEntity.getId()+"星");
			}
		}
		return list;
		
	} 
}
