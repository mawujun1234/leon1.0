package com.mawujun.utils.spring.mvc;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.alibaba.fastjson.JSON;
import com.mawujun.utils.page.WhereInfo;

public class JsonStringToWhereInfo_FastJson  implements Converter<String, WhereInfo[]> {
	public WhereInfo[] convert(String arg0) {
//		//JSONObject params=JSONObjectUtils.fromJSON(arg0);
//		List<WhereInfo> list=new ArrayList<WhereInfo>();
//		//JSONArray wheres=JSONArray.fromObject(arg0);
//		
//		for(int i=0;i<wheres.size();i++){
//			WhereInfo info=new WhereInfo();
//			JSONObject whereJson=wheres.getJSONObject(i);
//			if(whereJson.containsKey("op")){
//				info.setOp(whereJson.getString("op"));
//			}
//			if(whereJson.containsKey("property")){
//				info.setProperty(whereJson.getString("property"));
//			}
//			if(whereJson.containsKey("value")){
//				info.setValue(whereJson.getString("value"));
//			}
//			list.add(info);
//		}
//		
//		return list.toArray(new WhereInfo[list.size()]);
		
		List<WhereInfo> wheres=JSON.parseArray(arg0, WhereInfo.class);
		return wheres.toArray(new WhereInfo[wheres.size()]);
	}
}
