package com.mawujun.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.fun.FunVO;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository1.IRepository;
import com.mawujun.role.Role;
import com.mawujun.role.RoleService;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;

@Service
public class UserRoleService extends AbstractService<UserRole, UserRolePK> {
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunService funService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	/**
	 * 查询出所有的Role并且构建出Role的整棵树
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> queryRole(String userId){
		
		//List<Object> roleIds=super.queryListObject("queryRole", userId);//.selectListObj("queryRole", userId);
		List<String> roleIds=userRepository.queryRoleId(userId);
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
	
	public UserRolePK create(UserRole userRole){
		//注意还要互斥的角色判断
		userRole.setCreateDate(new Date());
		return super.create(userRole);
	}
	
	
	/**
	 * gen
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<FunVO> queryFun(String userId){	
		List<Map<String,Object>> funIdRoleIds = userRepository.queryFun(userId);//super.queryList("queryFun", userId);
		// 组装出role树
		Map<String,FunVO> parentKeys=new HashMap<String,FunVO>();
		List<FunVO> rootFuns = new ArrayList<FunVO>();
		for (Map<String,Object> funMap : funIdRoleIds) {
			//funes.add(funService.get(funId.toString()));
			
			
			String role_id=funMap.get("role_id").toString();
			Fun leaf=funService.get(funMap.get("fun_id").toString());
			
			if(parentKeys.get(leaf.getId()) !=null){//表示这个功能能已经添加过了
				Fun fun=parentKeys.get(leaf.getId());
				fun.addRoleName(roleService.get(role_id).getName());
				continue;
			}
			FunVO fun=new FunVO();
			BeanUtils.copyProperties(leaf, fun, new String[]{M.Fun.parent.name()});
			fun.addRoleName(roleService.get(role_id).getName());
			
			if(leaf.getParent()!=null){
				List<Fun> ancestores=leaf.findAncestors();
				int i=0;
				for(Fun ancestor:ancestores){
					FunVO ancestorNew=null;
					if(parentKeys.containsKey(ancestor.getId())){
						ancestorNew=parentKeys.get(ancestor.getId());
					} else {
						ancestorNew=new FunVO();
						BeanUtils.copyProperties(ancestor, ancestorNew, new String[]{"children","parent"});
						ancestorNew.setExpanded(true);
						parentKeys.put(ancestorNew.getId(), ancestorNew);
						if(ancestorNew.isLeaf()){
							ancestorNew.addRoleName(roleService.get(role_id).getName());
						}
					}
					if(i==0){
						
						ancestorNew.addChild(fun);
					} else {
						ancestorNew.addChild(parentKeys.get(ancestores.get(i-1).getId()));
					}
					i++;
					if(i==ancestores.size() && !rootFuns.contains(ancestorNew)){
						//添加从哪个角色来
						//ancestorNew.addRoleName(roleService.get(role_id).getName());
						rootFuns.add(ancestorNew);
					}
				}
			} else {
				
				rootFuns.add(fun);
				parentKeys.put(fun.getId(), fun);
			}
			
		}
		return rootFuns;
	}

	@Override
	public UserRoleRepository getRepository() {
		// TODO Auto-generated method stub
		return userRoleRepository;
	}
	
}
