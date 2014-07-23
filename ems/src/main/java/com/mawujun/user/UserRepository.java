package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.Params;

@Repository
public interface UserRepository extends IRepository<User, String> {
	public User getByUsername(String username);
	public void save(User user);
	public void update(User user);
	public void delete(String id);
	public List<User> list(Map<String,Object> param);
	public int changePwd(Map<String,Object> param);
	
	public List<FunRole> listFunRole(Map<String,Object> param);
	public void checkedFunRole(Map<String,Object> param);
	public void unCheckedFunRole(Map<String,Object> param);
	public List<String> selectAllCheckedFunRole(String user_id);
	public void deleteAllFunRole(String user_id);
	
	
	public List<DataRole> listDataRole(Map<String,Object> param);
	public void checkedDataRole(Map<String,Object> param);
	public void unCheckedDataRole(Map<String,Object> param);
	public List<String> selectAllCheckedDataRole(String user_id);
	public void deleteAllDataRole(String user_id);
	
	 public List<String> findRoles(String username);
	 public List<String> findPermissions(String username); 
}
