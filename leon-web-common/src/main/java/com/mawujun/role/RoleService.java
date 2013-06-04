package com.mawujun.role;

import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;

@Service
public class RoleService extends BaseRepository<Role, String> {


	public void update(Role entity,Boolean isUpdateParent,String oldParent_id) {	
		if(isUpdateParent!=null && isUpdateParent==true){
			
			//这里进行位置的移动
		} else {
			super.update(entity);
		}
	}

}
