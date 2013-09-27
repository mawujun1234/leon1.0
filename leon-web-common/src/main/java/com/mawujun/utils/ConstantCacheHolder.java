package com.mawujun.utils;

import com.mawujun.constant.ConstantItem;
import com.mawujun.constant.cache.IConstantCache;
import com.mawujun.constant.cache.SingleAppConstantCache;



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
