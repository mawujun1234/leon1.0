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
	
	public static void add(RoleFun roleFun){
		Role role=RoleCacheHolder.get(roleFun.getRole().getId());
		roleFun.setRole(role);
		role.addFun(roleFun);
	}
	public static void remove(RoleFun roleFun){
		RoleCacheHolder.get(roleFun.getRole().getId()).removeFun(roleFun);
		//RoleCacheHolder.get(roleRole.getCurrent().getId()).getOthers().remove(roleRole);
	}
	public static int size(){
		return roles.size();
	}
	
	/**
	 * 判断两个角色是否为祖先，即是否形成了循环
	 * @author mawujun 16064988@163.com 
	 * @return
	 */
	public static boolean hasChild(Role parent_p,Role child_p){
		Role parent=get(parent_p.getId());
		Role child=get(child_p.getId());
		return parent.isChild(child);
	}
	/**
	 * 用来获取权限的计算策略
	 * @author mawujun email:16064988@163.com qq:16064988
	 */
	public static AccessDecisionEnum getAccessDecisionEnum(){
		return AccessDecisionEnum.AffirmativeBased;
	}
//	/**
//	 * 
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param current
//	 * @param other
//	 * @return
//	 */
//	public static boolean hasMutex(Role current_p,Role other_p){
//		Role parent=get(current_p.getId());
//		Role child=get(other_p.getId());
//		
//	}
	
	
}
