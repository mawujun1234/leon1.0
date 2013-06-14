package com.mawujun.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<Map<String,Object>> queryByRole(String currentId,String roleRoleEnum){
		Map<String,String> params=new HashMap<String,String>();
		params.put("currentId", currentId);
		params.put("roleRoleEnum", roleRoleEnum);
		return super.queryList("queryByRole", params);
		
	}

}
