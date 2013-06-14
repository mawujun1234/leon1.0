package com.mawujun.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.role.Role;
import com.mawujun.role.RoleRole;
import com.mawujun.role.RoleService;

//@Component
public class RoleCacheHolder {

	private static ConcurrentHashMap<String,Role> roles=new ConcurrentHashMap<String,Role>();
//	static{
//		initCache();
//	}

	public static void initialize(){
		if(roles.size()==0){
			RoleService roleService=(RoleService)SpringContextHolder.getBean(RoleService.class);
			roleService.initCache();
		}
	}
	
	public static void add(Role role){
		roles.put(role.getId(), role);
	}
	
	public static void remove(Role role){
		roles.remove(role.getId());
	}
	public static void remove(Serializable id){
		roles.remove(id);
	}
	
	public static Role get(Serializable id){
		return roles.get(id);
	}
	
	public static void add(RoleRole roleRole){
		roleRole.setCurrent(RoleCacheHolder.get(roleRole.getCurrent().getId()));
		roleRole.setOther(RoleCacheHolder.get(roleRole.getOther().getId()));
	}
	public static void remove(RoleRole roleRole){
		RoleCacheHolder.get(roleRole.getCurrent().getId()).getCurrents().remove(roleRole);
		RoleCacheHolder.get(roleRole.getCurrent().getId()).getOthers().remove(roleRole);
	}
}
