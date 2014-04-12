package com.mawujun.role;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
@Repository
public interface RoleRepository extends IRepository<Role, String> {
	public List<Map<String,Object>> queryRoleUrl();
}
