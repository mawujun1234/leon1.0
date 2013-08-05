package com.mawujun.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.User;

@Service
public class UserGroupService extends BaseRepository<UserGroup, UserGroupPK> {

//	public List<User> queryUser(String groupId,String userName){
//		Map<String,String> params=new HashMap<String,String>();
//		params.put("group_id", groupId);
//		params.put("userName", "%"+userName+"%");
//		List<User> users=super.queryList("queryUser",params,User.class);
//		return users;
//	}

}
