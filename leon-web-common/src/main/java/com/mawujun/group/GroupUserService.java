package com.mawujun.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.user.User;
import com.mawujun.utils.page.Page;

@Service
public class GroupUserService extends AbstractService<GroupUser, GroupUserPK> {
	@Autowired
	private GroupUserRepository groupUserRepository;

	@Override
	public GroupUserRepository getRepository() {
		// TODO Auto-generated method stub
		return groupUserRepository;
	}
	


//	public List<User> queryUser(String groupId,String userName){
//		Map<String,String> params=new HashMap<String,String>();
//		params.put("group_id", groupId);
//		params.put("userName", "%"+userName+"%");
//		List<User> users=super.queryList("queryUser",params,User.class);
//		return users;
//	}

}
