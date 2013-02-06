package com.mawujun.constant.cache;

import com.mawujun.constant.domain.CodeItem;


/**
 * 
 * @author mawujun
 *
 */
public class CodeCacheHolder {
	
	public static ICodeCache codeCache=new SingleAppCodeCache();
	
	public static CodeItem  get(String codeClassId,String code){
		return codeCache.get(codeClassId, code);
	}
	
	public void setCodeCache(ICodeCache codeCache){
		CodeCacheHolder.codeCache=codeCache;
	}

}
