package com.mawujun.role;

import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.repository.BaseRepository;

@Service
public class RoleRoleService extends BaseRepository<RoleRole, RoleRole.Id>{
	public void create(RoleRole entity) {
		
		RoleCacheHolder.add(entity);
		super.create(entity);

	}
	
	public void delete(RoleRole entity) {
		super.delete(entity);
		RoleCacheHolder.remove(entity);
	}
}
