package com.mawujun.controller.spring.mvc;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.alibaba.fastjson.JSON;
import com.mawujun.utils.page.SortInfo;
/**
 * SortInfo[]不能直接作为Controller方法的参数，必须包含在某个对象中，这样才可以进行转换
 * 
 * @author mawujun
 *
 */
//@Deprecated
public class JsonStringToSortInfo_FastJson  implements Converter<String, SortInfo[]> {
	public SortInfo[] convert(String arg0) {
//		//JSONObject params=JSONObjectUtils.fromJSON(arg0);
//		List<SortInfo> list=new ArrayList<SortInfo>();
//		JSONArray sorts=JSONArray.fromObject(arg0);
//		for(int i=0;i<sorts.size();i++){
//			SortInfo info=new SortInfo();	
//			JSONObject sortJson=sorts.getJSONObject(i);
//			if(sortJson.containsKey("property")){
//				info.setProperty(sortJson.getString("property"));
//			}
//			if(sortJson.containsKey("direction")){
//				info.setDirection(sortJson.getString("direction"));
//			}
//
//			list.add(info);
//		}
//			
//		
//		return list.toArray(new SortInfo[list.size()]);
		List<SortInfo> sorts=JSON.parseArray(arg0, SortInfo.class);
		return sorts.toArray(new SortInfo[sorts.size()]);
	}
}