package com.mawujun.user;


import java.util.List;

import org.springframework.stereotype.Service;

import com.mawujun.repository.BaseRepository;

@Service
public class UserService  extends BaseRepository<User, String>{
	
	/**
	 * 注意UserRoleService的
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<String> queryRoleId(String userId) {
		return super.queryList("queryRole", userId,String.class);
	}
 
}
