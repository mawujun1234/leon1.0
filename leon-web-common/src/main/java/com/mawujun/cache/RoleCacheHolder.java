package com.mawujun.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.role.Role;
import com.mawujun.role.AccessDecisionEnum;
import com.mawujun.role.RoleFun;
import com.mawujun.role.RoleRole;
import com.mawujun.role.RoleService;

//@Component
public class RoleCacheHolder {

	//private static ConcurrentHashMap<String,Role> roles=new ConcurrentHashMap<String,Role>();

//	static RoleService roleService=null;
//	public static void initialize(){
//		roleService=(RoleService)SpringContextHolder.getBean(RoleService.class);
//	}
//	
//	public static void add(Role role){
//		//roles.put(role.getId(), role);
//	}
//	
//	public static void remove(Role role){
//		//roles.remove(role.getId());
//	}
//	public static void remove(Serializable id){
//		//roles.remove(id);
//	}
//	
//	public static Role get(Serializable id){
//		//return roles.get(id);
//		return roleService.get(id);
//	}
//	
//
//	public static int size(){
//		return roles.size();
//	}
//	

}
