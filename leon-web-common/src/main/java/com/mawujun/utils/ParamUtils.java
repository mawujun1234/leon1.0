package com.mawujun.utils;

import java.util.HashMap;

/**
 * Hashmap的参数构成器
 * ParamUtils params=ParamUtils.init().put("parameterId", parameterId).put("subjectType", subjectType);
 * this.queryList("query_SYSTEM", params,ParameterSubjectVO.class);
 * @author mawujun email:160649888@163.com qq:16064988
 *
 */
public class ParamUtils extends HashMap<String, Object> {
	//Map<String,Object> paams=new HashMap<String,Object>();
	public static ParamUtils init(){
		return new ParamUtils();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  ParamUtils add(String key,Object value){
		super.put(key, value);
		return this;
	}
	/**
	 * 如果value==null，就不添加这儿条件
	 * @author mawujun 16064988@qq.com 
	 * @param key
	 * @param value
	 * @return
	 */
	public ParamUtils addIf(String key,Object value){
		if(value!=null){
			super.put(key, value);
		}		
		return this;
	}
	public ParamUtils put(String key,Object value){
		super.put(key, value);
		return this;
	}
	
	/**
	 * 如果value==null，就不添加这儿条件
	 * @author mawujun 16064988@qq.com 
	 * @param key
	 * @param value
	 * @return
	 */
	public ParamUtils putIf(String key,Object value){
		if(value!=null){
			super.put(key, value);
		}		
		return this;
	}

}
