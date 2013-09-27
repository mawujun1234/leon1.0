package com.mawujun.role;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.BaseRepository;
import com.mawujun.utils.RoleCacheHolder;
import com.mawujun.utils.page.WhereInfo;

@Service
public class RoleService extends BaseRepository<Role, String> {
//	public Role get(Serializable id) {
//		//Role role=RoleCacheHolder.get(id);
////		if(role!=null){
////			return role;
////		}
//		return super.get(id);
//	}
//	public Role create(Role entity) {
//		super.create(entity);
//	}
//	
//	public Role delete(Role entity) {
//		super.delete(entity);
//	}


	public void update(Role entity,Boolean isUpdateParent,String oldParent_id) {	
		if(isUpdateParent!=null && isUpdateParent==true){
			
			//这里进行位置的移动
		} else {
			super.update(entity);
		}
		
		this.get(entity.getId()).setName(entity.getName());
	}
	
	
//	public Set<Role> queryMutex(String ownId) {
//		Role own=this.get(ownId);
//		super.initLazyProperty(own.getMutex());
//		return own.getMutex();
//	}
//	
//	public void addMutex(String ownId,String mutexId){
//		if(ownId.equals(mutexId)){
//			throw new BusinessException("不能设置自己为自己的互斥角色");
//		}
//
//		Role own=this.get(ownId);
//		Role mutex=this.get(mutexId);
////		//如果已经存在互斥关系的话，那也不能添加继承关系了
////		if(own.isInherit(mutex)){
////			throw new BusinessException("存在继承关系的两个角色不能设为互斥角色!");
////		}
//		own.addMutex(mutex);
//		mutex.addMutex(own);
//		super.update(own);
//		super.update(mutex);
//	}
//	public void removeMutex(String ownId,String mutexId) {
//		Role own=this.get(ownId);
//		Role mutex=this.get(mutexId);
//		own.removeMutex(mutex);
//		mutex.removeMutex(own);
//		super.update(own);
//		super.update(mutex);
//	}
	public void initCache(){
		//WhereInfo whereinfo=WhereInfo.parse("roleEnum", RoleEnum.role);
		List<Role> roles=super.queryAll();//super.query(whereinfo);
		for(Role role:roles){
			//if(role.getRoleEnum()==RoleEnum.role){
				//super.initLazyProperty(role.getParents());
				//super.initLazyProperty(role.getChildren());
				//super.initLazyProperty(role.getMutex());
//				role.getMutex().size();
//				for (Role roleFun:role.getMutex()){
//					System.out.println(roleFun.getId());
//				}
				
				super.initLazyProperty(role.getFunes());
//				for (RoleFun roleFun:role.getFunes()){
//					System.out.println(roleFun.getId());
//				}

			//}	
		}
	}
	
	public List<Map<String,Object>> queryRoleUrl(){
		return super.queryList("queryRoleUrl");
	}
	
//	public List<Map<String,Object>> queryByRole(String otherId,String roleRoleEnum){
//		Map<String,String> params=new HashMap<String,String>();
//		params.put("otherId", otherId);
//		params.put("roleRoleEnum", roleRoleEnum);
//		return super.queryList("queryByRole", params);
//		
//	}
//	public Set<Role> queryParent(String childId) {
//		Role child=RoleCacheHolder.get(childId);
//		return child.getParents();
//	}
	
	
//	public void addParent(String parentId,String childId){
//		Role parent=RoleCacheHolder.get(parentId);
//		Role child=RoleCacheHolder.get(childId);
//		
//		if(child.isChild(parent)){
//			throw new BusinessException("不能把《"+parent.getName()+"》设置为《"+child.getName()+"》的父角色,会出现死循环!");
//			
//			
//		}
//		//再检查是不是互斥，如果是互斥的话就不能添加，如果和另一个类的父类或子类互斥 那也不能添加
//		if(child.isMutex(parent)){
//			throw new BusinessException("不能把《"+parent.getName()+"》设置为《"+child.getName()+"》的父角色,因为角色中有冲突角色的设置!");
//		}
//		
//		//Collections.synchronizedList(new ArrayList());
//		//删除角色的时候还有问题，要删除所有关联的数据
//		
//		child.addParent(parent);
//		parent.addChild(child);
//		super.update(parent);
//		super.update(child);
//	}

	
//	public void removeParent(String parentId,String childId){
//		Role parent=RoleCacheHolder.get(parentId);
//		Role child=RoleCacheHolder.get(childId);
//		child.removeParent(parent);
//		parent.removeChild(child);
//		super.update(parent);
//		super.update(child);
//	}
	

	

	
}
