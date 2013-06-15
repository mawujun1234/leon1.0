package com.mawujun.role;

import org.springframework.stereotype.Service;

import com.mawujun.cache.RoleCacheHolder;
import com.mawujun.exception.BussinessException;
import com.mawujun.repository.BaseRepository;

@Service
public class RoleRoleService extends BaseRepository<RoleRole, RoleRole.Id>{
	public void create(RoleRole entity) {
		//判断是否有循环角色了
		if(RoleRoleEnum.inherit==entity.getRoleRoleEnum()){
			if(RoleCacheHolder.hasChild(entity.getChild(),entity.getParent())){
				throw new BussinessException("不能把《"+RoleCacheHolder.get(entity.getParent().getId()).getName()+"》设置为《"+RoleCacheHolder.get(entity.getChild().getId()).getName()+"》的父角色,会出现死循环!");
			}
			
			//再检查时不是互斥，如果是互斥的话就不能添加，如果和另一个类的父类或子类互斥 那也不能添加
			
			
			
		}
		//Collections.synchronizedList(new ArrayList());
		//删除角色的时候还有问题，要删除所有关联的数据
		
		
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
