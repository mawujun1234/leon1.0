package com.mawujun.role;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.repository.BaseRepository;

@Service
public class RoleService extends BaseRepository<Role, String> {
	public Role get(Serializable id) {
		Role role=RoleCacheHolder.get(id);
		if(role!=null){
			return role;
		}
		return super.get(id);
	}
	public void create(Role entity) {
		super.create(entity);
		RoleCacheHolder.add(entity);
	}
	
	public void delete(Role entity) {
		super.delete(entity);
		RoleCacheHolder.remove(entity);
	}


	public void update(Role entity,Boolean isUpdateParent,String oldParent_id) {	
		if(isUpdateParent!=null && isUpdateParent==true){
			
			//这里进行位置的移动
		} else {
			super.update(entity);
		}
		RoleCacheHolder.get(entity.getId()).setName(entity.getName());
	}
	
	public List<Map<String,Object>> queryByRole(String otherId,String roleRoleEnum){
		Map<String,String> params=new HashMap<String,String>();
		params.put("otherId", otherId);
		params.put("roleRoleEnum", roleRoleEnum);
		return super.queryList("queryByRole", params);
		
	}

	public void initCache(){
		List<Role> roles=super.queryAll();
		for(Role role:roles){
			if(role.getRoleEnum()==RoleEnum.role){
				super.initLazyProperty(role.getCurrents());
				super.initLazyProperty(role.getOthers());
				RoleCacheHolder.add(role);
				//System.out.println(role.getName()+"============================");
			}	
		}
	}
}
