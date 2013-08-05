package com.mawujun.repository.mybatis;

import java.util.HashMap;

/**
 * Hashmap的参数构成器
 * Params params=Params.init().put("parameterId", parameterId).put("subjectType", subjectType);
 * this.queryList("query_SYSTEM", params,ParameterSubjectVO.class);
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class Params extends HashMap<String, Object> {
	//Map<String,Object> paams=new HashMap<String,Object>();
	public static Params init(){
		return new Params();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  Params add(String key,Object value){
		super.put(key, value);
		return this;
	}
	public Params put(String key,Object value){
		super.put(key, value);
		return this;
	}

}
