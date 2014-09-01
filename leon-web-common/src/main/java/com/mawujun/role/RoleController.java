package com.mawujun.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.fun.Fun;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.T;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.page.WhereInfo;

@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleFunService roleFunService;
	@Resource(name="roleFunObservers")
	List<RoleFunObserver>  roleFunObservers;
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
		
		return roleService.queryFun(Page.getInstance(start, limit).addParam(T.leon_role_fun.role_id, roleId));
	}

	@RequestMapping("/role/addFun")
	@ResponseBody
	public RoleFun create(String roleId, String funId, String permissionEnum) {
		// 继承的权限如何覆盖和取消。
		// 父角色的权限不能取消，当修改权限属性的时候，如果发现时父权限，就新建一个权限
		RoleFun roleFun = new RoleFun();
		roleFun.setRole(new Role(roleId));
		roleFun.setFun(new Fun(funId));
		roleFun.setPermissionEnum(permissionEnum);

		roleFunService.create(roleFun);

		// filterInvocationSecurityMetadataSourceImpl.addConfigAttribute(roleFun.getFun().getUrl(),
		// roleFun.getRole().getId());
		notifyObservers(roleFun.getRole(), roleFun.getFun(), true);
		
		JsonConfigHolder.setFilterPropertys(M.RoleFun.role.name(),M.RoleFun.fun.name());
		return roleFun;
	}
	
	@RequestMapping("/role/removeFun")
	@ResponseBody
	public RoleFun destroy(String roleId, String funId) {
		RoleFun roleFun = roleFunService.delete(roleId, funId);
		notifyObservers(roleFun.getRole(), roleFun.getFun(), false);
		// filterInvocationSecurityMetadataSourceImpl.removeConfigAttribute(roleFun.getFun().getUrl(),
		// roleId);

		JsonConfigHolder.setFilterPropertys(M.RoleFun.role.name(),M.RoleFun.fun.name());

		return roleFun;
	}
	
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param role
	 * @param fun
	 * @param createOrDestroy true 表示创建了，false表示是删除
	 */
	protected void notifyObservers(Role role,Fun fun,boolean createOrDestroy){
		if(roleFunObservers!=null){
			for(RoleFunObserver obser:roleFunObservers){
				if(createOrDestroy){
					obser.create(role, fun);
				} else {
					obser.destroy(role, fun);
				}
				
			}
		}
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
