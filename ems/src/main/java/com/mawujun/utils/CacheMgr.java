package com.mawujun.utils;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

@Component(value="cacheMgr")
public class CacheMgr {
	@Resource(name="cacheManager")
	private EhCacheCacheManager cacheManager;
	
	public void putQrcode(String key,Map<String,Object> value){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		cache.put(key, value);
		//Element element = new Element(key, value);
		//cache.put(element);
	}
	
	public Map<String,Object> getQrcode(String key){
		Cache cache = cacheManager.getCache("qrcodeCache");
		//Element element = cache.get(key);
		return cache.get(key, Map.class);
	}
	
	

}
