package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
@Repository
public interface FunRoleRepository  extends IRepository<FunRole, String>{
	public void save(FunRole funRole);
	public void update(FunRole funRole);
	public void delete(String id);
	public List<FunRole> list(Map<String,Object> param);
	
	public List<Navigation> listNav4Comm(Map param);
	public List<Navigation> listNav4Checked(Map param);
	public void checkedNavigation(Map param);
	public void unCheckedNavigation(Map param);
	public List<String> selectAllCheckedNav(String funRole_id);
	public void deleteLeftNavication(String id);
}