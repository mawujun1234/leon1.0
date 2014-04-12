package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Repository
public interface UserRepository extends IRepository<User, String> {
	public List<String> queryRoleId(String userId);
	public List<Map<String,Object>> queryFun(String userId);
	
	public Page querySwitchUsers(Page pages);
	
	public int querySwitchUsersCount(Params params);
}
