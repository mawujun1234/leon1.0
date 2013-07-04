package com.mawujun.cache;

import com.mawujun.controller.spring.SpringContextHolder;
import com.mawujun.fun.FunService;
import com.mawujun.role.RoleService;

public class CacheInit {

	public  void initialize(){
			//RoleService roleService=(RoleService)SpringContextHolder.getBean(RoleService.class);
		RoleService roleService=(RoleService)SpringContextHolder.getBean(RoleService.class);
		roleService.initCache();
		
		FunService funService=(FunService)SpringContextHolder.getBean(FunService.class);
		funService.initCache();
	}
}
