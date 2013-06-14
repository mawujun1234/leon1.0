package com.mawujun.role;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.utils.page.WhereInfo;

@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	/**
	 * 桌面程序中把菜单按权限读取出来
	 * @return
	 */
	@RequestMapping("/role/query")
	@ResponseBody
	public List<Role> query(String id) {
		List<Role> roles=null;
		if(!"root".equals(id)){
			WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
			roles=roleService.query(whereinfo);
		} else {
			roles=roleService.query();
		}

		return roles;
	}
	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/role/queryAll")
	@ResponseBody
	public List<Role> queryAll(){		
		return roleService.queryAll();
	}
	@RequestMapping("/role/load")
	@ResponseBody
	public Role load(String id){		
		return roleService.get(id);
	}
	
	@RequestMapping("/role/create")
	@ResponseBody
	public Role create(@RequestBody Role role){	
		if(role.getParent()!=null&&"root".equals(role.getParent().getId())){
			role.setParent(null);
		}
		roleService.create(role);
		//int i=RoleCacheHolder.size();
		return role;
	}
	
	@RequestMapping("/role/update")
	@ResponseBody
	public Role update(@RequestBody Role role,Boolean isUpdateParent,String oldParent_id){		
		 roleService.update(role,isUpdateParent,oldParent_id);
		 return role;
	}
	
	@RequestMapping("/role/destroy")
	@ResponseBody
	public Role destroy(@RequestBody Role role){		
		roleService.delete(role);
		return role;
	}
	
	
	@RequestMapping("/role/queryByRole")
	@ResponseBody
	public List<Map<String,Object>> queryByRole(String otherId,String roleRoleEnum) {
		List<Map<String,Object>> roles=null;

//		WhereInfo whereinfo=WhereInfo.parse("current.id", currentId);
//		WhereInfo whereinfo1=WhereInfo.parse("current.id", roleRoleEnum);
		roles=roleService.queryByRole(otherId,roleRoleEnum);

		return roles;
	}
	
}
