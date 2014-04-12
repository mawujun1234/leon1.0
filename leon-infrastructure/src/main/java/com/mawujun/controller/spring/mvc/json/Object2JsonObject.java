package com.mawujun.controller.spring.mvc.json;

import java.util.Collection;
import java.util.Map;

import org.springframework.ui.ModelMap;

public class Object2JsonObject implements Trans2JsonObject {

	@Override
	public Map convert(Object object) {
		ModelMap map=new ModelMap();
		map.put(JsonConfigHolder.getRootName(), object);
		
		if(object instanceof Collection){
			map.put(JsonConfigHolder.getTotalName(), JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():((Collection)object).size());
		} else {
			Class c=object.getClass();
			if(c.isArray()){
				map.put(JsonConfigHolder.getTotalName(), JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():((Object[])object).length);
			} else {
				map.put(JsonConfigHolder.getTotalName(),JsonConfigHolder.getTotal()!=null?JsonConfigHolder.getTotal():1);
			}

		}
		
		if(JsonConfigHolder.getMsg()!=null){
			map.put(JsonConfigHolder.getMsgName(), JsonConfigHolder.getMsg());
		}
		
		map.put(JsonConfigHolder.getSuccessName(), JsonConfigHolder.getSuccessValue());
		return map;
	}

	@Override
	public boolean supports(Object object) {
		// TODO Auto-generated method stub
		return true;
	}

}
