package com.mawujun.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.group.GroupUserService;
import com.mawujun.parameter.ParameterSubjectService;
import com.mawujun.parameter.SubjectType;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;

@Service
public class UserService  extends BaseRepository<User, String>{
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	GroupUserService groupUserService;
	@Autowired
	private ParameterSubjectService parameterSubjectService;
	/**
	 * 注意UserRoleService的
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<String> queryRoleId(String userId) {
		return super.queryList("queryRole", userId,String.class);
	}
 
	public void delete(User entity) {
		//删除和角色的uganxi
		//super.deleteBatch(Cnd.delete().ad)
		userRoleService.deleteBatch(Cnd.delete().andEquals("id.userId", entity.getId()));
		//删除和组的关系
		groupUserService.deleteBatch(Cnd.delete().andEquals("id.userId", entity.getId()));
		//删除和参数的关系
		parameterSubjectService.deleteBatch(Cnd.delete().andEquals("subjectId",  entity.getId()).andEquals("subjectType", SubjectType.USER));
		
		super.delete(entity);
	}
	
	public void recover(String id) {
		Cnd cnd=Cnd.update().set("deleted", false).andEquals("id", id);
		super.update(cnd);
	}
}
