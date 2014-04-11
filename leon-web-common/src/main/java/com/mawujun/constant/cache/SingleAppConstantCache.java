package com.mawujun.constant.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mawujun.constant.Constant;
import com.mawujun.constant.ConstantItem;
import com.mawujun.constant.ConstantService;
import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.page.WhereInfo;


/**
 * 这是没有集群的时候可以使用这个类来缓存数据，如果使用集群，请继承ICodeCache重新实现缓存
 * @author mawujun
 *
 */
public class SingleAppConstantCache implements IConstantCache {
	
	private static Map<String,ConstantItem> cache=new HashMap<String,ConstantItem>();
	/**
	 * 缓存所有的CodeItem,CodeClassService.cacheCode();
	 */
	public void cacheCodeByCodeClass(List<Constant> list){
		SingleAppConstantCache.cache=new HashMap<String,ConstantItem>();;
		for(Constant codeClass:list){
			if(codeClass.getConstantItemes()!=null){
				for(ConstantItem codeItem:codeClass.getConstantItemes()){
					cache.put(codeItem.getConstant().getId()+"=="+codeItem.getCode(), codeItem);
				}
			}	
		}
		
	}
	/**
	 * 缓存所有的CodeItem,CodeItemService.cacheCode();
	 */
	public void cacheCode(List<ConstantItem> list){
		SingleAppConstantCache.cache=new HashMap<String,ConstantItem>();;
			if(list!=null){
				for(ConstantItem codeItem:list){
					cache.put(codeItem.getConstant().getId()+"=="+codeItem.getCode(), codeItem);
				}
			}	
	}
	public void  put(String key,ConstantItem codeItem){
		cache.put(codeItem.getConstant().getId()+"=="+codeItem.getCode(), codeItem);
	}
	/**
	 * 会有不存在这个codeItem的情况
	 * @param constantCode
	 * @param code
	 * @return
	 */
	public ConstantItem  get(String constantCode,String codeItemCode){
		ConstantItem codeItem= cache.get(constantCode+"=="+codeItemCode);
		if(codeItem==null){
			ConstantService constantService=SpringContextHolder.getBean(ConstantService.class);
			
			//Constant constant=constantService.queryUnique(WhereInfo.parse("code", constantCode));//(constantCode);g
			Constant constant=constantService.queryUnique(Cnd.select().andEquals(M.Constant.code, constantCode));//(constantCode);g
			if(constant.getConstantItemes()!=null){
				for(ConstantItem codeItem1:constant.getConstantItemes()){
					cache.put(codeItem1.getConstant().getCode()+"=="+codeItem1.getCode(), codeItem1);
				}
			}	
		}
		return codeItem;
	}
}
