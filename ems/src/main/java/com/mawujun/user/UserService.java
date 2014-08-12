package com.mawujun.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;

@Service
public class UserService  extends AbstractService<User, String>{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserStoreRepository userStoreRepository;
	
	@Override
	public IRepository<User, String> getRepository() {
		// TODO Auto-generated method stub
		return userRepository;
	}
	
	public User getByUsername(String username){
		return userRepository.getByUsername(username);
	}
	 public Set<String> findRoles(String username){
		 List<String> list= userRepository.findRoles(username);
		 Set<String> set=new HashSet<String>();
		 set.addAll(list);
		 return set;
	 }
	 public Set<String> findPermissions(String username){
		 List<String> list= userRepository.findPermissions(username);
		 Set<String> set=new HashSet<String>();
		 set.addAll(list);
		 return set;
	 }
	
	public String save(User user){
		if(this.getByUsername(user.getUsername())!=null){
			throw new BusinessException("已经存在相同的用户名");
		}
		user.setId(UUID.randomUUID().toString());
		
		userRepository.create(user);
		return user.getId();
	}
//	public String update(User user){
//		userRepository.update(user);
//		return user.getId();
//	}
	public void delete(String id){
//		userRepository.delete(id);
//		userRepository.deleteAllFunRole(id);
//		userRepository.deleteAllDataRole(id);
		userRepository.update(Cnd.update().set(M.User.status, false));
	}
	
	public int changePwd(String username,String password_old,String password_new) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("username", username);
		param.put("password_old", password_old);
		param.put("password_new", password_new);
		return userRepository.changePwd(param);
	}
	public List<User> listUserByFunRole(String funrole_id){
		//Map<String,Object> param=new HashMap<String,Object>();
		//param.put("parentId", parentId);
		return userRepository.listUserByFunRole(funrole_id);
	}
	
	public List<FunRole> listFunRole(String parentId,String user_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("user_id", user_id);
		return userRepository.listFunRole(param);
	}
	
	public void checkedFunRole(String user_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("funRole_id", funRole_id);
		param.put("user_id", user_id);
		userRepository.checkedFunRole(param);
	}
	
	public void unCheckedFunRole(String user_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("funRole_id", funRole_id);
		param.put("user_id", user_id);
		userRepository.unCheckedFunRole(param);
	}
	
	public List<String> selectAllCheckedFunRole(String user_id) {
		return userRepository.selectAllCheckedFunRole(user_id);
	}
	
	
//	public List<DataRole> listDataRole(String parentId,String user_id) {
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("parentId", parentId);
//		param.put("user_id", user_id);
//		return userRepository.listDataRole(param);
//	}
//	
//	public void checkedDataRole(String user_id,String dataRole_id) {
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("dataRole_id", dataRole_id);
//		param.put("user_id", user_id);
//		userRepository.checkedDataRole(param);
//	}
//	
//	public void unCheckedDataRole(String user_id,String dataRole_id) {
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("dataRole_id", dataRole_id);
//		param.put("user_id", user_id);
//		userRepository.unCheckedDataRole(param);
//	}
//	
//	public List<String> selectAllCheckedDataRole(String user_id) {
//		return userRepository.selectAllCheckedDataRole(user_id);
//	}
	
	public void checkchangeStore(String user_id,String store_id,Boolean checked) {
		if(checked==true){
			UserStore userStore=new UserStore();
			userStore.setStore_id(store_id);
			userStore.setUser_id(user_id);
			userStoreRepository.create(userStore);
		} else if(checked==false){
			userStoreRepository.deleteBatch(Cnd.delete().andEquals(M.UserStore.store_id, store_id).andEquals(M.UserStore.user_id, user_id));
		}
	}
	
	public List<String> selectAllCheckedStore(String user_id) {
		List<UserStore> userStores=userStoreRepository.query(Cnd.select().andEquals(M.UserStore.user_id, user_id));
		List<String> storeIds=new ArrayList<String>();
		for(UserStore userStore:userStores){
			storeIds.add(userStore.getStore_id());
		}
		return storeIds;
	}
	
	
	

}
