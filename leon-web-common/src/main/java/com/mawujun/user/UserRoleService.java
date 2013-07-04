package com.mawujun.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.role.Role;
import com.mawujun.role.RoleService;
import com.mawujun.utils.page.WhereInfo;

@Service
public class UserRoleService extends BaseRepository<UserRole, UserRolePK> {
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunService funService;
	
	public List<Map<String,Object>> queryRole(String userId){
		////String hql="select b.id.roleId from UserRole b where b.id.userId=?";
		//List<UserRole> userRoles=super.query(WhereInfo.parse("id.userId", userId));
		
		List<Object> roleIds=super.getMybatisRepository().selectListObj("queryRole", userId);
		//组装出role树
		List<Role> roles=new ArrayList<Role>();
		for(Object roleId:roleIds){
			roles.add(roleService.get(roleId.toString()));
		}
		
		//倒推出整棵树
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		Map<String,Map<String,Object>> parentKeys=new HashMap<String,Map<String,Object>>();
		for(Role role:roles){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("iconCls", role.getIconCls());
			map.put("leaf", true);
			
			List<Role>  categories=role.findCategories();
			 
			if(categories!=null && categories.size()>0){
				
				Map<String,Object> category_temp=null;

				for(Role category:categories){
					Map<String,Object> categorymap=new HashMap<String,Object>();
					if(parentKeys.containsKey(category.getId())){
						categorymap=parentKeys.get(category.getId());
					} else {
						categorymap.put("id", category.getId());
						categorymap.put("name", category.getName());
						categorymap.put("iconCls", category.getIconCls());
						categorymap.put("expanded", true);
						
						List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
						categorymap.put("children",children);
						
						parentKeys.put(category.getId(), categorymap);
					}
					List<Map<String,Object>> children=(List<Map<String,Object>>)categorymap.get("children");
					if(category_temp==null) {
						children.add(map);
					} else {
						children.add(category_temp);
					}	
					
					category_temp=categorymap;
					
				}
				//添加根category
				result.add(category_temp);
			} else {
				result.add(map);
			}
			
		}
		return result;
	}
	
	public void create(UserRole userRole){
		//注意还要互斥的角色判断
		userRole.setCreateDate(new Date());
		super.create(userRole);
	}
	
	
	/**
	 * gen
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<Fun> queryFun(String userId){	
		List<Object> roleIds = super.getMybatisRepository().selectListObj("queryFun", userId);
		// 组装出role树
		List<Fun> funes = new ArrayList<Fun>();
		for (Object funId : funes) {
			funes.add(funService.get(funId.toString()));
		}
		//构建出功能树
		
		sd
		return null;
	}
	
}
