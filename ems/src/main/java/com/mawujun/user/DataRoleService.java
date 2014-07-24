package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dataRoleService")
@Transactional
public class DataRoleService {
	@Resource(name="dataRoleRepository")
	private DataRoleRepository dataRoleRepository;
	
	public String save(DataRole dataRole){
		dataRole.setId(UUID.randomUUID().toString());
		
		dataRoleRepository.save(dataRole);
		return dataRole.getId();
	}
	public String update(DataRole dataRole){
		dataRoleRepository.update(dataRole);
		return dataRole.getId();
	}
	public void delete(String id){
		dataRoleRepository.delete(id);
	}
	public List<DataRole> list(String parentId){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return dataRoleRepository.list(param);
	}
}
