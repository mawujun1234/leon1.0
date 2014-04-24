package com.mawujun.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.group.GroupUserService;
import com.mawujun.parameter.ParameterSubjectService;
import com.mawujun.parameter.SubjectType;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Service
public class UserService  extends AbstractService<User, String>{
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	GroupUserService groupUserService;
	@Autowired
	private ParameterSubjectService parameterSubjectService;
	@Autowired
	private UserRepository userRepository;
	/**
	 * 注意UserRoleService的
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param userId
	 * @return
	 */
	public List<String> queryRoleId(String userId) {
		return this.getRepository().queryRoleId(userId);
		//return super.queryList("queryRole", userId,String.class);
	}
 
	public void delete(User entity) {
		//删除和角色的uganxi
		//super.deleteBatch(Cnd.delete().ad)
		userRoleService.deleteBatch(Cnd.delete().andEquals(M.UserRole.id.userId, entity.getId()));
		//删除和组的关系
		groupUserService.deleteBatch(Cnd.delete().andEquals(M.GroupUser.id.userId, entity.getId()));
		//删除和参数的关系
		//parameterSubjectService.deleteBatch(Cnd.delete().andEquals("subjectId",  entity.getId()).andEquals("subjectType", SubjectType.USER));
		parameterSubjectService.deleteBatch(Cnd.delete().andEquals(M.ParameterSubject.id.subjectId,  entity.getId())
				.andEquals(M.ParameterSubject.id.subjectType, SubjectType.USER));
		
		super.delete(entity);
	}
	
	public void recover(String id) {
		//Cnd cnd=Cnd.update().set("deleted", false).set("deletedDate", null).andEquals("id", id);
		Cnd cnd=Cnd.update().set(M.User.deleted, false).set(M.User.deletedDate, null).andEquals(M.User.id, id);
		super.update(cnd);
	}
	
	public void resetPwd(String id,String password) {
		super.update(Cnd.update().set(M.User.password, password).andEquals(M.User.id, id));
	}

	public int querySwitchUsersCount(String masterId,String j_username){
		int count=this.getRepository().querySwitchUsersCount(Params.init().add("masterId", masterId).add("j_username", j_username));
		return count;
	}
	
	public Page queryFun(Page page){
		return this.getRepository().queryFun(page);
	}
	@Override
	public UserRepository getRepository() {
		// TODO Auto-generated method stub
		return userRepository;
	}
}
