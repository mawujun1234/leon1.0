package com.mawujun.controller.spring.mvc.json;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.mawujun.utils.page.QueryResult;

public class QueryResult2JsonObject implements Trans2JsonObject {

	@Override
	public Map convert(Object obj) {
		QueryResult page=(QueryResult)obj;
		ModelMap map=new ModelMap();
		map.put(JsonConfigHolder.getRootName(), page.getResult());
		map.put(JsonConfigHolder.getTotalName(), page.getTotalItems());
		map.put(JsonConfigHolder.getStartName(), page.getStart());
		map.put(JsonConfigHolder.getLimitName(), page.getPageSize());
		map.put(JsonConfigHolder.getPageNoName(), page.getPageNo());
		
		if(JsonConfigHolder.getMsg()!=null){
			map.put(JsonConfigHolder.getMsgName(), JsonConfigHolder.getMsg());
		}
		
		map.put(JsonConfigHolder.getSuccessName(), JsonConfigHolder.getSuccessValue());
		return map;
	}

	@Override
	public boolean supports(Object object) {
		// TODO Auto-generated method stub
		if(object instanceof QueryResult){
			return true;
		}
		return false;
	}

}
