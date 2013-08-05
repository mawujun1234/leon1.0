package com.mawujun.controller.spring.mvc.other;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.core.convert.converter.Converter;

import com.mawujun.utils.page.WhereInfo;

public class StringToWhereInfo_JsonLib implements Converter<String, WhereInfo[]>  {

	public WhereInfo[] convert(String arg0) {
		//JSONObject params=JSONObjectUtils.fromJSON(arg0);
		List<WhereInfo> list=new ArrayList<WhereInfo>();
		JSONArray wheres=JSONArray.fromObject(arg0);
		for(int i=0;i<wheres.size();i++){
			WhereInfo info=new WhereInfo();
			JSONObject whereJson=wheres.getJSONObject(i);
			if(whereJson.containsKey("op")){
				info.setOp(whereJson.getString("op"));
			}
			if(whereJson.containsKey("property")){
				info.setProp(whereJson.getString("property"));
			}
			if(whereJson.containsKey("value")){
				info.setValue(whereJson.getString("value"));
			}
			list.add(info);
		}
		
		return list.toArray(new WhereInfo[list.size()]);
	}

}
