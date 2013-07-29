package com.mawujun.role;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.controller.spring.mvc.JsonConfigHolder;
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
			WhereInfo whereinfo=WhereInfo.parse("category.id", id);
			roles=roleService.query(whereinfo);
		} else {
			WhereInfo whereinfo=WhereInfo.parse("category.id_isNull", "11");
			roles=roleService.query(whereinfo);
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
		if(role.getCategory()!=null&&"root".equals(role.getCategory().getId())){
			role.setCategory(null);
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
	@RequestMapping("/role/queryMutex")
	@ResponseBody
	public Set<Role> queryMutex(String ownId) {
		Set<Role> roles=null;
		roles=roleService.queryMutex(ownId);
		roles.size();
		JsonConfigHolder.setFilterPropertys("parents,children,mutex,funes");
		return roles;
	}
	
	@RequestMapping("/role/addMutex")
	@ResponseBody
	public void addMutex(String ownId,String mutexId){	
		roleService.addMutex(ownId, mutexId);
	}
	
	@RequestMapping("/role/removeMutex")
	@ResponseBody
	public void removeMutex(String ownId,String mutexId){	
		roleService.removeMutex(ownId, mutexId);
	}
	
//	@RequestMapping("/role/addParent")
//	@ResponseBody
//	public void addParent(String parentId,String childId){	
//		roleService.addParent(parentId, childId);
//	}
//	
//
//	
//	@RequestMapping("/role/removeParent")
//	@ResponseBody
//	public void removeParent(String parentId,String childId){	
//		roleService.removeParent(parentId, childId);
//	}
//	
//	
//	
//	@RequestMapping("/role/queryParent")
//	@ResponseBody
//	public Set<Role> queryParent(String childId) {
//		Set<Role> roles=null;
//		roles=roleService.queryParent(childId);
//		JsonConfigHolder.setFilterPropertys("parents,children,mutex,funes");
//		return roles;
//	}
	
	
}
