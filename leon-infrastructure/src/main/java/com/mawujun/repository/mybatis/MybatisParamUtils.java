package com.mawujun.repository.mybatis;

import java.util.HashMap;

/**
 * Hashmap的参数构成器
 * MybatisParamUtils params=MybatisParamUtils.init().put("parameterId", parameterId).put("subjectType", subjectType);
 * this.queryList("query_SYSTEM", params,ParameterSubjectVO.class);
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class MybatisParamUtils extends HashMap<String, Object> {
	//Map<String,Object> paams=new HashMap<String,Object>();
	public static MybatisParamUtils init(){
		return new MybatisParamUtils();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  MybatisParamUtils add(String key,Object value){
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
	public MybatisParamUtils addIf(String key,Object value){
		if(value!=null){
			super.put(key, value);
		}		
		return this;
	}
	public MybatisParamUtils put(String key,Object value){
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
	public MybatisParamUtils putIf(String key,Object value){
		if(value!=null){
			super.put(key, value);
		}		
		return this;
	}

}
