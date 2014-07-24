package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("funRoleService")
@Transactional
public class FunRoleService {
	@Resource(name="funRoleRepository")
	private FunRoleRepository funRoleRepository;
	
	public String save(FunRole funRole){
		funRole.setId(UUID.randomUUID().toString());
		
		funRoleRepository.save(funRole);
		return funRole.getId();
	}
	public String update(FunRole funRole){
		funRoleRepository.update(funRole);
		return funRole.getId();
	}
	public void delete(String id){
		funRoleRepository.delete(id);
		funRoleRepository.deleteLeftNavication(id);
	}
	public List<FunRole> list(String parentId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return funRoleRepository.list(param);
	}
	
	public List<Navigation> listNav4Comm(String parentId,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("funRole_id", funRole_id);
		return funRoleRepository.listNav4Comm(param);
	}
	public List<Navigation> listNav4Checked(String parentId,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("funRole_id", funRole_id);
		return funRoleRepository.listNav4Comm(param);
	}
	
	public void checkedNavigation(String navigation_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("funRole_id", funRole_id);
		funRoleRepository.checkedNavigation(param);
	}
	public void unCheckedNavigation(String navigation_id,String funRole_id) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("funRole_id", funRole_id);
		
		funRoleRepository.unCheckedNavigation(param);
	}
	public List<String> selectAllCheckedNav(String funRole_id) {
		return funRoleRepository.selectAllCheckedNav(funRole_id);
	}
}
