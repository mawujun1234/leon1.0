package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("service.dataRole")
@Transactional
public class DataRoleServiceImpl {
	@Resource(name="mapper.dataRole")
	private DataRoleMapper dateRoleMapper;
	
	public String save(DataRole dataRole){
		dataRole.setId(UUID.randomUUID().toString());
		
		dateRoleMapper.save(dataRole);
		return dataRole.getId();
	}
	public String update(DataRole dataRole){
		dateRoleMapper.update(dataRole);
		return dataRole.getId();
	}
	public void delete(String id){
		dateRoleMapper.delete(id);
	}
	public List<DataRole> list(String parentId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return dateRoleMapper.list(param);
	}
}
