package com.mawujun.role;

import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.repository.BaseRepository;

@Service
public class RoleRoleService extends BaseRepository<RoleRole, RoleRole.Id>{
	public void create(RoleRole entity) {
		//判断是否有循环角色了
		if(RoleRoleEnum.inherit==entity.getRoleRoleEnum() && RoleCacheHolder.hasChild(entity.getChild(),entity.getParent())){
			throw new BussinessException("不能把《"+RoleCacheHolder.get(entity.getParent().getId()).getName()+"》设置为《"+RoleCacheHolder.get(entity.getChild().getId()).getName()+"》的父角色,会出现死循环!");
		}
		
		
		RoleCacheHolder.add(entity);
		super.create(entity);

	}
	
	public void delete(RoleRole entity) {
		super.delete(entity);
		RoleCacheHolder.remove(entity);
	}
	
	public void delete(RoleRole.Id id) {
		RoleRole roleRole=super.get(id);
		super.delete(roleRole);
		RoleCacheHolder.remove(roleRole);
	}
}
