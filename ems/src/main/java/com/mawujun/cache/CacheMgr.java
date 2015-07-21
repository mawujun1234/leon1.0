package com.mawujun.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.mawujun.store.IEcodeCache;
import com.mawujun.utils.page.Page;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

@Component(value="cacheMgr")
public class CacheMgr {
	@Resource(name="cacheManager")
	private EhCacheCacheManager cacheManager;
	
	public void putQrcode(EquipKey key,IEcodeCache value){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			cache.put(key, new LinkedHashMap<String,IEcodeCache>());
			element=cache.get(key);
		}
		((Map<String,IEcodeCache>)element.get()).put(value.getEcode(), value);
		
	}
	public void removeQrcode(EquipKey key,String ecode){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element!=null){
			((Map<String,IEcodeCache>)element.get()).remove(ecode);
			if(((Map<String,IEcodeCache>)element.get()).size()==0){
				cache.evict(key);
			}
		}
	}
	public void clearQrcode(EquipKey key){		
		Cache cache = cacheManager.getCache("qrcodeCache");
		cache.evict(key);
	}
	
	public IEcodeCache getQrcode(EquipKey key,String ecode){
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			return null;
		}
		Map<String,IEcodeCache> map=(Map<String,IEcodeCache>)element.get();
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
		LinkedHashMap<String,IEcodeCache> map=(LinkedHashMap<String,IEcodeCache>)element.get();
		//map.values();
		
		ArrayList<IEcodeCache> list=new ArrayList<IEcodeCache>();
		int size=map.size();
		//当当前页没有数据的时候，就获取前一页的数据
		if(start>=size){
			start=(start-limit<10)?0:(start-limit);
		}
		
		int i=0;
		for(Entry<String,IEcodeCache> entry:map.entrySet()) {
			if((i+1)>start){
				if(((size-i-1))<=limit){
					list.add(entry.getValue());
				} else {
					//break;
				}	
			}
			i++;
//			if((size-i-1)>=start){
//				if(((size-i-1)-start)<limit){
//					list.add(entry.getValue());
//				} else {
//					//break;
//				}	
//			}
//			i++;
		}
		
		//Collections.reverse(list);
		Page page=Page.getInstance(start, limit);
		page.setTotal(map.size());
		page.setResult(list);
						
		
		return page;
	}
	
	public IEcodeCache[] getQrcodesAll(EquipKey key){
		Cache cache = cacheManager.getCache("qrcodeCache");
		ValueWrapper element = cache.get(key);
		if(element==null){
			return null;
		}
		LinkedHashMap<String,IEcodeCache> map=(LinkedHashMap<String,IEcodeCache>)element.get();
		return map.values().toArray(new IEcodeCache[map.size()]);
	}
	
	

}
