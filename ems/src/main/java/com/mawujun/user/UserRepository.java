package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface UserRepository extends IRepository<User, String> {
	public User getByUsername(@Param("username")String username);
	public void save(User user);
	public void update(User user);
	public void delete(String id);
	public List<User> listUserByFunRole(@Param("funrole_id")String funrole_id);
	public int changePwd(Map<String,Object> param);
	
	public List<FunRole> listFunRole(Map<String,Object> param);
	public void checkedFunRole(Map<String,Object> param);
	public void unCheckedFunRole(Map<String,Object> param);
	public List<String> selectAllCheckedFunRole(String user_id);
	public void deleteAllFunRole(String user_id);
	
	
	//public List<DataRole> listDataRole(Map<String,Object> param);
	//public void checkedDataRole(Map<String,Object> param);
	//public void unCheckedDataRole(Map<String,Object> param);
	//public List<String> selectAllCheckedDataRole(String user_id);
	//public void deleteAllDataRole(String user_id);
	
	 public List<String> findRoles(String username);
	 public List<String> findPermissions(String username); 
	 
	 
	 public List<String> selectAllCheckedStore(String user_id);
}
