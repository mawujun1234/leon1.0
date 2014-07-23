package com.mawujun.user;

import java.util.List;
import java.util.Map;

import com.mawujun.repository1.IRepository;

public interface FunRoleMapper  extends IRepository<FunRole, String>{
	public void save(FunRole funRole);
	public void update(FunRole funRole);
	public void delete(String id);
	public List<FunRole> list(Map<String,Object> param);
	
	public List<NavNode> listNav4Comm(Map param);
	public List<NavNode> listNav4Checked(Map param);
	public void checkedNavigation(Map param);
	public void unCheckedNavigation(Map param);
	public List<String> selectAllCheckedNav(String funRole_id);
	public void deleteLeftNavication(String id);
}
