package com.mawujun.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;


public class FunCacheHolder {
//	private static ConcurrentHashMap<String,Fun> funes=new ConcurrentHashMap<String,Fun>();
//	
//	public static void initialize(){
//		if(funes.size()==0){
//			FunService roleService=(FunService)SpringContextHolder.getBean(FunService.class);
//			roleService.initCache();
//		}
//	}
//	
//	public static void add(Fun fun){
//		funes.put(fun.getId(), fun);
//	}
//	
//	public static void remove(Fun fun){
//		funes.remove(fun.getId());
//	}
//	public static void remove(Serializable id){
//		funes.remove(id);
//	}
//	
//	public static Fun get(Serializable id){
//		return funes.get(id);
//	}
//	
//	
//	public static int size(){
//		return funes.size();
//	}
}
