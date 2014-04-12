package com.mawujun.controller.spring.mvc.json;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.mawujun.utils.page.Page;
import com.mawujun.utils.page.QueryResult;

/**
 * 把一个对象转换成JsonDataFormat的格式
 * @author mawujun 16064988@qq.com  
 *
 */
public class Page2JsonObject implements Trans2JsonObject {

	@Override
	public boolean supports(Object object) {
		if(object instanceof Page){
			return true;
		}
		return false;
	}

	@Override
	public Map convert(Object object) {
		Page page=(Page)object;
		ModelMap map=new ModelMap();
		map.put(JsonConfigHolder.getRootName(), page.getResult());
		map.put(JsonConfigHolder.getTotalName(), page.getTotal());
		map.put(JsonConfigHolder.getStartName(), page.getStart());
		map.put(JsonConfigHolder.getLimitName(), page.getPageSize());
		map.put(JsonConfigHolder.getPageNoName(), page.getPageNo());
		
		if(JsonConfigHolder.getMsg()!=null){
			map.put(JsonConfigHolder.getMsgName(), JsonConfigHolder.getMsg());
		}
		
		map.put(JsonConfigHolder.getSuccessName(), JsonConfigHolder.getSuccessValue());
		return map;
	}
}
