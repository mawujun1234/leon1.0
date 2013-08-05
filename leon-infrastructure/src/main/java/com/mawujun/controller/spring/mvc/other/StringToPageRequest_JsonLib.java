package com.mawujun.controller.spring.mvc.other;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.core.convert.converter.Converter;

import com.mawujun.utils.jsonLib.JSONObjectUtils;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.SortInfo;
import com.mawujun.utils.page.WhereInfo;

/**
 * 用处不大
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 承接各种项目
 *
 */
public class StringToPageRequest_JsonLib implements Converter<String, PageRequest> {

	public PageRequest convert(String arg0) {
		//JSONObject.fromObject(object, jsonConfig)
		JSONObject params=JSONObjectUtils.fromJSON(arg0);
		
		return StringToPageRequest_JsonLib.toPageRequest(params);
	}
	
	public static PageRequest toPageRequest(JSONObject params){
		PageRequest pageRequest=new PageRequest();
		pageRequest.setParams(params);
		int start=0;
		if(params.containsKey("start")){
			start=params.getInt("start");
		}
		int limit=-1;
		if(params.containsKey("limit")){
			limit=params.getInt("limit");
		}
		pageRequest.setStratAndLimit(start, limit);
		
		ArrayList<WhereInfo> whersList=new ArrayList<WhereInfo>();
		if(params.containsKey("wheres")){
			JSONArray wheres=params.getJSONArray("wheres");
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
				//pageRequest.addWheres(info);
				whersList.add(info);
			}
		}
		pageRequest.setWheres(whersList);
		
		ArrayList<SortInfo> sortsList=new ArrayList<SortInfo>();
		if(params.containsKey("sorts")){
			JSONArray sorts=params.getJSONArray("sorts");
			for(int i=0;i<sorts.size();i++){
				SortInfo info=new SortInfo();	
				JSONObject sortJson=sorts.getJSONObject(i);
				if(sortJson.containsKey("property")){
					info.setProp(sortJson.getString("property"));
				}
				if(sortJson.containsKey("direction")){
					info.setDir(sortJson.getString("direction"));
				}
				//pageRequest.addSorts(info);
				sortsList.add(info);
			}
		}
		pageRequest.setSorts(sortsList);

		// TODO Auto-generated method stub
		return pageRequest;
	}

}
