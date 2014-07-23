package com.mawujun.user;

import java.util.List;
import java.util.Map;

import com.mawujun.repository1.IRepository;

public interface DataRoleMapper  extends IRepository<DataRole, String>{
	public void save(DataRole dataRole);
	public void update(DataRole dataRole);
	public void delete(String id);
	public List<DataRole> list(Map<String,Object> param);
}
