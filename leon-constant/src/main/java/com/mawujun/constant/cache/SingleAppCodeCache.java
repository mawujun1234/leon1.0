package com.mawujun.constant.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.constant.domain.CodeItem;
import com.mawujun.constant.domain.CodeType;

/**
 * 这是没有集群的时候可以使用这个类来缓存数据，如果使用集群，请继承ICodeCache重新实现缓存
 * @author mawujun
 *
 */
public class SingleAppCodeCache implements ICodeCache {
	
	private static Map<String,CodeItem> cache=new HashMap<String,CodeItem>();
	/**
	 * 缓存所有的CodeItem,CodeClassService.cacheCode();
	 */
	public void cacheCodeByCodeClass(List<CodeType> list){
		SingleAppCodeCache.cache=new HashMap<String,CodeItem>();;
		for(CodeType codeClass:list){
			if(codeClass.getCodeItemes()!=null){
				for(CodeItem codeItem:codeClass.getCodeItemes()){
					cache.put(codeItem.getCodeType().getId()+"=="+codeItem.getCode(), codeItem);
				}
			}	
		}
		
	}
	/**
	 * 缓存所有的CodeItem,CodeItemService.cacheCode();
	 */
	public void cacheCode(List<CodeItem> list){
		SingleAppCodeCache.cache=new HashMap<String,CodeItem>();;
			if(list!=null){
				for(CodeItem codeItem:list){
					cache.put(codeItem.getCodeType().getId()+"=="+codeItem.getCode(), codeItem);
				}
			}	
	}
	public void  put(String key,CodeItem codeItem){
		cache.put(codeItem.getCodeType().getId()+"=="+codeItem.getCode(), codeItem);
	}
	/**
	 * 会有不存在这个codeItem的情况
	 * @param codeTypeId
	 * @param code
	 * @return
	 */
	public CodeItem  get(String codeTypeId,String code){
		CodeItem codeItem= cache.get(codeTypeId+"=="+code);
		return codeItem;
	}
}
