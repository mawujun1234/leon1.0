package com.mawujun.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.T;
import com.mawujun.utils.page.Page;
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
		System.out.println(Thread.currentThread().getId()+"==========================================");
		List<Role> roles=null;
		if(!"root".equals(id)){
			//WhereInfo whereinfo=WhereInfo.parse("category.id", id);
			roles=roleService.query(Cnd.where().andEquals(M.Role.category.id, id));
		} else {
			//WhereInfo whereinfo=WhereInfo.parse("category.id","is", "11");
			roles=roleService.query(Cnd.where().andIsNull(M.Role.category.id));
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
	
	@RequestMapping("/role/queryFun")
	@ResponseBody
	public Page  queryFun(Integer start,Integer limit,String roleId){
		
		return roleService.queryFun(Page.getInstance(start, limit).addParam(T.leon_Role_Fun.role_id, roleId));
//		Set<RoleFun> roleFunes=roleService.queryFun(roleId);
//		List<Map<String,Object>> funes=new ArrayList<Map<String,Object>>();
//		
//		for (RoleFun roleFun:roleFunes){
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("funId", roleFun.getFun().getId());
//
////			map.put("permissionEnum", roleFun.getPermissionEnum().toString());
////			//来源还没有做
////			StringBuffer buffer=new StringBuffer("");
////			for(RoleSource roleSource:roleFun.getRoleSources()){
////				buffer.append(roleSource.getName());
////				buffer.append(",");
////			}
////			map.put("roleSources", buffer);
////			map.put("fromParent", roleFun.isFromParent());
//			
//			funes.add(map);
//		}
//		return funes;
	}
	
	
//	@RequestMapping("/role/queryMutex")
//	@ResponseBody
//	public Set<Role> queryMutex(String ownId) {
//		Set<Role> roles=null;
//		roles=roleService.queryMutex(ownId);
//		roles.size();
//		JsonConfigHolder.setFilterPropertys("parents,children,mutex,funes");
//		return roles;
//	}
//	
//	@RequestMapping("/role/addMutex")
//	@ResponseBody
//	public void addMutex(String ownId,String mutexId){	
//		roleService.addMutex(ownId, mutexId);
//	}
//	
//	@RequestMapping("/role/removeMutex")
//	@ResponseBody
//	public void removeMutex(String ownId,String mutexId){	
//		roleService.removeMutex(ownId, mutexId);
//	}
	
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
