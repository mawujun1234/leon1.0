package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("service.funRole")
@Transactional
public class FunRoleServiceImpl {
	@Resource(name="mapper.funRole")
	private FunRoleMapper funRoleMapper;
	
	public String save(FunRole funRole){
		funRole.setId(UUID.randomUUID().toString());
		
		funRoleMapper.save(funRole);
		return funRole.getId();
	}
	public String update(FunRole funRole){
		funRoleMapper.update(funRole);
		return funRole.getId();
	}
	public void delete(String id){
		funRoleMapper.delete(id);
		funRoleMapper.deleteLeftNavication(id);
	}
	public List<FunRole> list(String parentId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return funRoleMapper.list(param);
	}
	
	public List<NavNode> listNav4Comm(String parentId,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("funRole_id", funRole_id);
		return funRoleMapper.listNav4Comm(param);
	}
	public List<NavNode> listNav4Checked(String parentId,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("funRole_id", funRole_id);
		return funRoleMapper.listNav4Comm(param);
	}
	
	public void checkedNavigation(String navigation_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("funRole_id", funRole_id);
		funRoleMapper.checkedNavigation(param);
	}
	public void unCheckedNavigation(String navigation_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("funRole_id", funRole_id);
		
		funRoleMapper.unCheckedNavigation(param);
	}
	public List<String> selectAllCheckedNav(String funRole_id) {
		return funRoleMapper.selectAllCheckedNav(funRole_id);
	}
}
