package com.mawujun.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.role.Role;
import com.mawujun.utils.page.WhereInfo;

@Service
public class UserRoleService extends BaseRepository<UserRole, UserRolePK> {
	public List<Role> queryRole(String userId){
		//String hql="select b.id.roleId from UserRole b where b.id.userId=?";
		List<UserRole> userRoles=super.query(WhereInfo.parse("id.userId", userId));
		
		return new ArrayList<Role>();
	}
	
	public void create(UserRole userRole){
		//注意还要互斥的角色判断
		super.create(userRole);
	}
	
}
