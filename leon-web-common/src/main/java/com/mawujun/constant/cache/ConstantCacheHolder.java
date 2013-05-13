package com.mawujun.constant.cache;

import com.mawujun.constant.ConstantItem;



/**
 * 
 * @author mawujun
 *
 */
public class ConstantCacheHolder {
	
	public static IConstantCache codeCache=new SingleAppConstantCache();
	
	public static ConstantItem  get(String codeClassId,String code){
		return codeCache.get(codeClassId, code);
	}
	
	public void setCodeCache(IConstantCache codeCache){
		ConstantCacheHolder.codeCache=codeCache;
	}

}
