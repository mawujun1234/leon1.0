package com.mawujun.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.role.Role;

@Service
public class UserRoleService extends BaseRepository<UserRole, UserRolePK> {
	public List<Role> queryRole(String roleId){
		return new ArrayList<Role>();
	}
	
	public void create(UserRole userRole){
		//注意还要互斥的角色判断
		super.create(userRole);
	}
	
}
