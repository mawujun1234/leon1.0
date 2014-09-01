package com.mawujun.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Element;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.mawujun.baseinfo.EquipmentVO;

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
			cache.put(key, new HashMap<String,EquipmentVO>());
		}
		((Map<String,EquipmentVO>)cache.get(key)).put(value.getEcode(), value);
		
	}
	public void removeQrcode(EquipKey key,String ecode){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element!=null){
			((Map<String,EquipmentVO>)element).remove(ecode);
			if(((Map<String,EquipmentVO>)element).size()==0){
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
		Map<String,EquipmentVO> map=(Map<String,EquipmentVO>)element.get();
		return map.get(ecode);
	}
	
	

}
