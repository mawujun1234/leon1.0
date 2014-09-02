package com.mawujun.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.utils.page.Page;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

@Component(value="cacheMgr")
public class CacheMgr {
	@Resource(name="cacheManager")
	private EhCacheCacheManager cacheManager;
	
	public void putQrcode(EquipKey key,EquipmentVO value){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			cache.put(key, new LinkedHashMap<String,EquipmentVO>());
			element=cache.get(key);
		}
		((Map<String,EquipmentVO>)element.get()).put(value.getEcode(), value);
		
	}
	public void removeQrcode(EquipKey key,String ecode){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element!=null){
			((Map<String,EquipmentVO>)element.get()).remove(ecode);
			if(((Map<String,EquipmentVO>)element.get()).size()==0){
				cache.evict(key);
			}
		}
	}
	public void clearQrcode(EquipKey key){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		cache.evict(key);
	}
	
	public EquipmentVO getQrcode(EquipKey key,String ecode){
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			return null;
		}
		Map<String,EquipmentVO> map=(Map<String,EquipmentVO>)element.get();
		return map.get(ecode);
	}
	/**
	 * 获取缓存中的条码，进行分页
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page getQrcodes(EquipKey key,Integer start,Integer limit){
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			return null;
		}
		LinkedHashMap<String,EquipmentVO> map=(LinkedHashMap<String,EquipmentVO>)element.get();
		//map.values();
		
		ArrayList<EquipmentVO> list=new ArrayList<EquipmentVO>();
		int size=map.size();
		
		int i=0;
		for(Entry<String,EquipmentVO> entry:map.entrySet()) {
			if((size-i-1)>=start){
				if(((size-i-1)-start)<limit){
					list.add(entry.getValue());
				} else {
					//break;
				}	
			}
			i++;
		}
		
		Collections.reverse(list);
		Page page=Page.getInstance(start, limit);
		page.setTotal(map.size());
		page.setResult(list);
						
		
		return page;
	}
	
	public EquipmentVO[] getQrcodesAll(EquipKey key){
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			return null;
		}
		LinkedHashMap<String,EquipmentVO> map=(LinkedHashMap<String,EquipmentVO>)element.get();
		return map.values().toArray(new EquipmentVO[map.size()]);
	}
	
	

}
